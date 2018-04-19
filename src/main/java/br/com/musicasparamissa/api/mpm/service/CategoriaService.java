package br.com.musicasparamissa.api.mpm.service;

import br.com.musicasparamissa.api.mpm.entity.Categoria;
import br.com.musicasparamissa.api.mpm.entity.Musica;
import br.com.musicasparamissa.api.mpm.entity.SugestaoMusica;
import br.com.musicasparamissa.api.exception.InvalidEntityException;
import br.com.musicasparamissa.api.exception.UnableToRemoveException;
import br.com.musicasparamissa.api.mpm.repository.CategoriaRepository;
import br.com.musicasparamissa.api.mpm.repository.ItemLiturgiaRepository;
import br.com.musicasparamissa.api.mpm.repository.MusicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private MusicaRepository musicaRepository;

    @Autowired
    private ItemLiturgiaRepository itemLiturgiaRepository;

    public Set<Categoria> search(String filter) {
        return categoriaRepository.findBySlugIgnoreCaseContainingOrNomeIgnoreCaseContainingOrDescricaoIgnoreCaseContaining(filter, filter, filter);
    }

    public Set<Categoria> getTree() {
        Set<Categoria> root = categoriaRepository.findByCategoriaMaeOrderByOrdem(null);
        root.forEach(c -> initializeChildren(c));
        return root;
    }

    private void initializeChildren(Categoria categoria) {
        categoria.getChildren().forEach(c -> initializeChildren(c));
    }

    public Categoria getCategoria(String slug) {
        return categoriaRepository.findOne(slug);
    }

    @Transactional
    public void save(Categoria categoria) throws InvalidEntityException {
        if(StringUtils.isEmpty(categoria.getCategoriaMae()))
            categoria.setCategoriaMae(null);
        categoriaRepository.save(categoria);
    }

    @Transactional
    public void delete(String slug) throws UnableToRemoveException {
        Categoria categoria = categoriaRepository.findOne(slug);
        if (categoria != null && categoria.getChildren().size() > 0) {
            throw new UnableToRemoveException("Esta categoria possui categorias filhas.");
        }
        List<Musica> musicas = musicaRepository.findByCategoria(categoria);
        musicas.forEach(musica -> {
            musica.getCategorias().remove(categoria);
            musicaRepository.save(musica);
        });
        List<SugestaoMusica> sugestoesMusicas = itemLiturgiaRepository.findByCategoria(categoria);
        sugestoesMusicas.forEach(sugestao -> {
            sugestao.getCategorias().remove(categoria);
            itemLiturgiaRepository.save(sugestao);
        });
        categoriaRepository.delete(categoria);
    }

}
