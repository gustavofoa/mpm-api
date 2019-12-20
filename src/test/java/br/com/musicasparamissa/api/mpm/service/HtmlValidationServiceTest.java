package br.com.musicasparamissa.api.mpm.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class HtmlValidationServiceTest {

    @InjectMocks
    private HtmlValidationService htmlValidationService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void validHtml() {
        String html = "<html><head><title></title></head><body>" +
                "<b>D</b>" +
                "TUDO QUE NÓS JÁ VIVEMOS.\n" +
                "<b>F#m</b>\n" +
                "TUDO QUE VAMOS VIVER.\n" +
                "<b>Em</b>                   <b>Bm9</b>\n" +
                "ELE É QUEM SABE O MOTIVO.\n" +
                "<b>Em</b>          <b>Bm9</b>\t   <b>Em</b>\n" +
                "ELE É QUEM PODE DIZER.\n" +
                "<b>Em</b>\n" +
                "ELE É QUEM SABE A VERDADE.\n" +
                "<b>Bm</b>\n" +
                "ELE É QUEM MOSTRA O CAMINHO.\n" +
                "<b>G</b>                  <b>A</b>                 <b>D</b>   <b>A7</b>\n" +
                "E QUEM PROCURA POR ELE, NÃO VIVE SOZINHO.\n" +
                "<b>D</b>\n" +
                "ELE É O PÃO E O VINHO.\n" +
                "<b>F#m</b>\n" +
                "ELE É O PRINCÍPIO E O FIM.\n" +
                "<b>Em</b>                 <b>Bm9</b>\n" +
                "ELE É O REI E O CORDEIRO.\n" +
                "<b>Em</b>      <b>Bm9</b>     <b>Em</b>\n" +
                "ELE É O NÃO E O SIM.\n" +
                "<b>Em</b>                    <b>Bm</b>\n" +
                "ELE SÓ QUER ALEGRIA, RISOS E FELICIDADE.\n" +
                "  <b>G</b>                <b>A</b>                <b>D</b>    <b>A7</b>\n" +
                "E PAZ NA TERRA AOS HOMENS DE BOA VONTADE.\n" +
                "\n" +
                "          <b>D</b>\n" +
                "<strong>VAMOS CANTAR PARABÉNS PRA JESUS,</strong>\n" +
                "      <b>F#m</b>\n" +
                "<strong>COMEMORAR PARABÉNS PRA JESUS.</strong>\n" +
                "     <b>G</b>            <b>Bm9</b>     <b>Em</b>\n" +
                "<strong>NOS ABRAÇAR NESSA NOITE FELIZ.</strong>\n" +
                "          <b>C</b>                <b>A</b>\n" +
                "<strong>EM QUE O AMOR ASCENDEU SUA LUZ.</strong>\n" +
                "         <b>D</b>\n" +
                "<strong>VAMOS CANTAR PARABÉNS PRA JESUS,</strong>\n" +
                "<b>F#m</b>\n" +
                "<strong>COMEMORAR PARABÉNS PRA JESUS.</strong>\n" +
                "     <b>G</b>            <b>Bm9</b>     <b>Em</b>\n" +
                "<strong>NOS ABRAÇAR NESSA NOITE FELIZ.</strong>\n" +
                "           <b>G</b>                 <b>A</b>\n" +
                "<strong>A ESTRELA GUIA DO CÉU NOS CONDUZ.</strong>\n" +
                "               <b>D</b>\n" +
                "<strong>PARABÉNS PRA JESUS.</strong>\n" +
                "\n" +
                "<b>D</b>\n" +
                "ELE É O MELHOR AMIGO.\n" +
                "<b>F#m</b>\n" +
                "ELE É O PAI E O FILHO.\n" +
                "<b>Em</b>                   <b>Bm9</b>\n" +
                "ELE É MAIOR DO QUE A MORTE.\n" +
                "<b>Em</b>     <b>Bm9</b>\t<b>Em</b>\n" +
                "É O DESTINO E O TRILHO.\n" +
                "<b>Em</b>      \n" +
                "ELE É CARINHO MAIS DOCE.\n" +
                "<b>Bm</b>\n" +
                "ELE É A FLOR E A SEMENTE.\n" +
                "<b>G</b>                                 <b>A</b>         <b>D</b>   <b>A7</b>\n" +
                "ELE É QUEM SABE O QUE EXISTE AQUI DENTRO DA GENTE.\n" +
                "<b>D</b>\n" +
                "ELE É A ÁGUA MAIS PURA.\n" +
                "<b>F#m</b>\n" +
                "ELE É O SOL E O LUAR.\n" +
                "<b>Em</b>             <b>Bm9</b>\n" +
                "ELE VENCEU O DESERTO\n" +
                "<b>Em</b>          <b>Bm9</b>      <b>Em</b>\n" +
                "E ANDOU NAS ÁGUAS DO MAR.\n" +
                "<b>Em</b>\n" +
                "ELE É O MESTRE DOS SÁBIOS.\n" +
                "<b>Bm</b>\n" +
                "ELE É O REI E O SENHOR.\n" +
                "<b>G</b>                 <b>D</b>                  <b>A</b>    <b>D7</b>\n" +
                "ELE POR MIM DEU A VIDA, EM NOME DO AMOR.\n" +
                "\n" +
                "          <b>D</b>\n" +
                "<strong>VAMOS CANTAR PARABÉNS PRA JESUS,</strong>\n" +
                "      <b>F#m</b>\n" +
                "<strong>COMEMORAR PARABÉNS PRA JESUS.</strong>\n" +
                "     <b>G</b>            <b>Bm9</b>     <b>Em</b>\n" +
                "<strong>NOS ABRAÇAR NESSA NOITE FELIZ.</strong>\n" +
                "          <b>C</b>                <b>A</b>\n" +
                "<strong>EM QUE O AMOR ASCENDEU SUA LUZ.</strong>\n" +
                "         <b>D</b>\n" +
                "<strong>VAMOS CANTAR PARABÉNS PRA JESUS,</strong>\n" +
                "<b>F#m</b>\n" +
                "<strong>COMEMORAR PARABÉNS PRA JESUS.</strong>\n" +
                "     <b>G</b>            <b>Bm9</b>     <b>Em</b>\n" +
                "<strong>NOS ABRAÇAR NESSA NOITE FELIZ.</strong>\n" +
                "           <b>G</b>                 <b>A</b>\n" +
                "<strong>A ESTRELA GUIA DO CÉU NOS CONDUZ.</strong>\n" +
                "               <b>D</b>\n" +
                "<strong>PARABÉNS PRA JESUS.</strong>\n" +
                "</body></html>";
        Assert.assertTrue(htmlValidationService.validateHtml(html));
    }

    @Test
    public void invalidHtml() {
        String html = "<html><head><title></title></head><body>" +
                "<b>D</b>" +
                "TUDO QUE NÓS JÁ VIVEMOS.\n" +
                "<b>F#m</b>\n" +
                "TUDO QUE VAMOS VIVER.\n" +
                "<b>Em</b>                   <b>Bm9</b>\n" +
                "ELE É QUEM SABE O MOTIVO.\n" +
                "<b>Em</b>          <b>Bm9</b>\t   <b>Em</b>\n" +
                "ELE É QUEM PODE DIZER.\n" +
                "<b>Em</b>\n" +
                "ELE É QUEM SABE A VERDADE.\n" +
                "<b>Bm</b>\n" +
                "ELE É QUEM MOSTRA O CAMINHO.\n" +
                "<b>G</b>                  <b>A</b>                 <b>D</b>   <b>A7</b>\n" +
                "E QUEM PROCURA POR ELE, NÃO VIVE SOZINHO.\n" +
                "<b>D</b>\n" +
                "ELE É O PÃO E O VINHO.\n" +
                "<b>F#m</b>\n" +
                "ELE É O PRINCÍPIO E O FIM.\n" +
                "<b>Em</b>                 <b>Bm9</b>\n" +
                "ELE É O REI E O CORDEIRO.\n" +
                "<b>Em</b>      <b>Bm9</b>     <b>Em</b>\n" +
                "ELE É O NÃO E O SIM.\n" +
                "<b>Em</b>                    <b>Bm</b>\n" +
                "ELE SÓ QUER ALEGRIA, RISOS E FELICIDADE.\n" +
                "  <b>G</b>                <b>A</b>                <b>D</b>    <b>A7</b>\n" +
                "E PAZ NA TERRA AOS HOMENS DE BOA VONTADE.\n" +
                "\n" +
                "          <b>D</b>\n" +
                "<strong>VAMOS CANTAR PARABÉNS PRA JESUS,</strong>\n" +
                "      <b>F#m</b>\n" +
                "<strong>COMEMORAR PARABÉNS PRA JESUS.</strong>\n" +
                "     <b>G</b>            <b>Bm9</b>     <b>Em</b>\n" +
                "<strong>NOS ABRAÇAR NESSA NOITE FELIZ.</strong>\n" +
                "          <b>C</b>                <b>A</b>\n" +
                "<strong>EM QUE O AMOR ASCENDEU SUA LUZ.</strong>\n" +
                "         <b>D</b>\n" +
                "<strong>VAMOS CANTAR PARABÉNS PRA JESUS,</strong>\n" +
                "<b>F#m</b>\n" +
                "<strong>COMEMORAR PARABÉNS PRA JESUS.</strong>\n" +
                "     <b>G</b>            <b>Bm9</b>     <b>Em</b>\n" +
                "<strong>NOS ABRAÇAR NESSA NOITE FELIZ.</strong>\n" +
                "           <b>G</b>                 <b>A</b>\n" +
                "<strong>A ESTRELA GUIA DO CÉU NOS CONDUZ.</strong>\n" +
                "               <b>D</b>\n" +
                "<strong>PARABÉNS PRA JESUS.</strong>\n" +
                "\n" +
                "<b>D</b>\n" +
                "ELE É O MELHOR AMIGO.\n" +
                "<b>F#m</b>\n" +
                "ELE É O PAI E O FILHO.\n" +
                "<b>Em</b>                   <b>Bm9</b>\n" +
                "ELE É MAIOR DO QUE A MORTE.\n" +
                "<b>Em</b>     <b>Bm9</b>\t<b>Em</b>\n" +
                "É O DESTINO E O TRILHO.\n" +
                "<b>Em</b>      \n" +
                "ELE É CARINHO MAIS DOCE.\n" +
                "<b>Bm</b>\n" +
                "ELE É A FLOR E A SEMENTE.\n" +
                "<b>G</b>                                 <b>A</b>         <b>D</b>   <b>A7</b>\n" +
                "ELE É QUEM SABE O QUE EXISTE AQUI DENTRO DA GENTE.\n" +
                "<b>D</b>\n" +
                "ELE É A ÁGUA MAIS PURA.\n" +
                "<b>F#m</b>\n" +
                "ELE É O SOL E O LUAR.\n" +
                "<b>Em</b>             <b>Bm9</b>\n" +
                "ELE VENCEU O DESERTO\n" +
                "<b>Em</b>          <b>Bm9</b>      <b>Em</b>\n" +
                "E ANDOU NAS ÁGUAS DO MAR.\n" +
                "<b>Em</b>\n" +
                "ELE É O MESTRE DOS SÁBIOS.\n" +
                "<b>Bm</b>\n" +
                "ELE É O REI E O SENHOR.\n" +
                "<b>G</b>                 <b>D</b>                  <b>A</b>    <b>D7</b>\n" +
                "ELE POR MIM DEU A VIDA, EM NOME DO AMOR.\n" +
                "\n" +
                "          <b>D</b>\n" +
                "VAMOS CANTAR PARABÉNS PRA JESUS,</strong>\n" +
                "      <b>F#m</b>\n" +
                "<strong>COMEMORAR PARABÉNS PRA JESUS.</strong>\n" +
                "     <b>G</b>            <b>Bm9</b>     <b>Em</b>\n" +
                "<strong>NOS ABRAÇAR NESSA NOITE FELIZ.</strong>\n" +
                "          <b>C</b>                <b>A</b>\n" +
                "<strong>EM QUE O AMOR ASCENDEU SUA LUZ.</strong>\n" +
                "         <b>D</b>\n" +
                "<strong>VAMOS CANTAR PARABÉNS PRA JESUS,</strong>\n" +
                "<b>F#m</b>\n" +
                "<strong>COMEMORAR PARABÉNS PRA JESUS.</strong>\n" +
                "     <b>G</b>            <b>Bm9</b>     <b>Em</b>\n" +
                "<strong>NOS ABRAÇAR NESSA NOITE FELIZ.</strong>\n" +
                "           <b>G</b>                 <b>A</b>\n" +
                "<strong>A ESTRELA GUIA DO CÉU NOS CONDUZ.</strong>\n" +
                "               <b>D</b>\n" +
                "<strong>PARABÉNS PRA JESUS.</strong>\n" +
                "</body></html>";
        Assert.assertFalse(htmlValidationService.validateHtml(html));
    }

}