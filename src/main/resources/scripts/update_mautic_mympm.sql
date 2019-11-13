DROP procedure IF EXISTS `update_mautic_mympm`;

DELIMITER $$
USE `mpm`$$
CREATE DEFINER=`mpm`@`%` PROCEDURE `update_mautic_mympm`()
BEGIN

#1. Inserir no mautic emails não cadastrados

	INSERT INTO mautic_mpm.leads(is_published, date_added, date_modified, points, firstname, email)
	select 1, sysdate(), sysdate(), 1, u.nome, u.email
    from mpm.mympm_usuario u
	left join mautic_mpm.leads l on l.email = u.email
	where l.email is null;

#2. Adicionar usuários premium ao segmento "Premium" (id: 3)

	insert into mautic_mpm.lead_lists_leads(leadlist_id, lead_id, date_added, manually_removed, manually_added)
	select 3, l.id, sysdate(), 0, 1 from mpm.mympm_usuario u
	inner join mautic_mpm.leads l on l.email = u.email
	left join mautic_mpm.lead_lists_leads ll on ll.lead_id = l.id and ll.leadlist_id = 3
	where u.premium = 1 and ll.lead_id is null;

#3. Adicionar usuários "não premium" no segmento "Não premium" (id: 4)

	insert into mautic_mpm.lead_lists_leads(leadlist_id, lead_id, date_added, manually_removed, manually_added)
	select 4, l.id, sysdate(), 0, 1 from mpm.mympm_usuario u
	inner join mautic_mpm.leads l on l.email = u.email
	left join mautic_mpm.lead_lists_leads ll on ll.lead_id = l.id and ll.leadlist_id = 4
	where u.premium = 0 and ll.lead_id is null;

#4. Remover usuários "não premium" do segmento "Premium" (id: 3)

	delete from mautic_mpm.lead_lists_leads
	where leadlist_id = 3 and lead_id not in
	(select l.id from mpm.mympm_usuario u
	inner join mautic_mpm.leads l on l.email = u.email
	and u.premium = 1);

#5. Remover usuários "premium" do segmento "Não premium" (id: 4)

	delete from mautic_mpm.lead_lists_leads
	where leadlist_id = 4 and lead_id not in
	(select l.id from mpm.mympm_usuario u
	inner join mautic_mpm.leads l on l.email = u.email
	and u.premium = 0);

#6. Remover todos os usuários dos segmento diferentes de 3 e 4

	delete from mautic_mpm.lead_lists_leads
	where leadlist_id not in (3,4) and lead_id in
	(select l.id from mpm.mympm_usuario u
	inner join mautic_mpm.leads l on l.email = u.email);

END$$

DELIMITER ;
