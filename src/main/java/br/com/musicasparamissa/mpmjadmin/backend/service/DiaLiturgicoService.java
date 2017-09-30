package br.com.musicasparamissa.mpmjadmin.backend.service;

import br.com.musicasparamissa.mpmjadmin.backend.entity.*;
import br.com.musicasparamissa.mpmjadmin.backend.repository.DiaLiturgicoRepository;
import br.com.musicasparamissa.mpmjadmin.backend.repository.ItemLiturgiaRepository;
import br.com.musicasparamissa.mpmjadmin.backend.repository.MusicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;


@Service
public class DiaLiturgicoService {
	
	@Autowired
	private DiaLiturgicoRepository diaLiturgicoRepository;

	@Autowired
	private ItemLiturgiaRepository itemLiturgiaRepository;

	@Autowired
	private MusicaRepository musicaRepository;

    public boolean exists(String slug) {
        return diaLiturgicoRepository.exists(slug);
    }

	public DiaLiturgico getDiaLiturgico(String slug) {
		return diaLiturgicoRepository.findOne(slug);
	}

    public Page<DiaLiturgico> search(String filter, Pageable pageable){
        return diaLiturgicoRepository.findBySlugIgnoreCaseContainingOrTituloIgnoreCaseContaining(filter, filter, pageable);
    }

	public Set<ItemLiturgia> listItems(DiaLiturgico diaLiturgico) {
		return itemLiturgiaRepository.findByDiaLiturgicoOrderByPosicao(diaLiturgico);
	}

	public Set<Musica> listMusicasSugeridas(SugestaoMusica item) {
		Set<Musica> lista = new HashSet<>();
		addMusicasFromCategorias(item, lista);
		addMusicasAvulsas(item, lista);
		removeMusicasARemover(item, lista);
		return lista;
	}

    public ItemLiturgia getItemLiturgia(Long id) {
        return itemLiturgiaRepository.findOne(id);
    }

	private void removeMusicasARemover(SugestaoMusica item, Set<Musica> lista) {
		if(item.getRemover()!=null){
			lista.removeAll(item.getRemover());
		}
	}

	private void addMusicasAvulsas(SugestaoMusica item, Set<Musica> lista) {
		if(item.getAvulsas()!=null){
			lista.addAll(item.getAvulsas());
		}
	}

	private void addMusicasFromCategorias(SugestaoMusica item, Set<Musica> lista) {
		if(item.getCategorias()!=null){
			for(Categoria categoria : item.getCategorias()){
				lista.addAll(musicaRepository.findByCategoria(categoria));
			}
		}
	}

	@Transactional
	public void save(DiaLiturgico diaLiturgico) {
		diaLiturgicoRepository.save(diaLiturgico);
	}

    @Transactional
    public ItemLiturgia save(ItemLiturgia item) {
        return itemLiturgiaRepository.save(item);
    }

	@Transactional
	public void delete(DiaLiturgico diaLiturgico) {
		itemLiturgiaRepository.delete(listItems(diaLiturgico));
		diaLiturgicoRepository.delete(diaLiturgico);
	}

	@Transactional
	public void delete(ItemLiturgia item) {
		itemLiturgiaRepository.delete(item);
	}

}
