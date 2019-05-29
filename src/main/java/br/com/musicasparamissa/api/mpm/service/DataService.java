package br.com.musicasparamissa.api.mpm.service;

import br.com.musicasparamissa.api.mpm.entity.Data;
import br.com.musicasparamissa.api.exception.InvalidEntityException;
import br.com.musicasparamissa.api.mpm.repository.DataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Date;

@Service
public class DataService {

	@Autowired
	private DataRepository dataRepository;

	@Autowired
	private BannerService bannerService;

	@Autowired
	private SiteGenerateService siteGenerateService;

	public Page<Data> listDatas(Pageable pageable){
		return dataRepository.findAllByOrderByDataDesc(pageable);
	}

	@Transactional
	public void update(Data data, Date oldDate) throws InvalidEntityException {
		validate(data);
		if(oldDate != null){
			dataRepository.delete(oldDate);
		}

		if(data.getDataCadastro() == null)
			data.setDataCadastro(LocalDate.now());
		data.setDataUltimaEdicao(LocalDate.now());

		dataRepository.save(data);
	}

    @Transactional
    public void create(Data data) throws InvalidEntityException {
        validate(data);

		if(data.getDataCadastro() == null)
			data.setDataCadastro(LocalDate.now());
		data.setDataUltimaEdicao(LocalDate.now());

		dataRepository.save(data);

		bannerService.refreshOne(data.getLiturgia());

		siteGenerateService.generateOnlyPaginaSugestao(data.getLiturgia(), siteGenerateService.getContext());

    }

	private void validate(Data data) throws InvalidEntityException {
		if(data.getData() == null){
			throw new InvalidEntityException("Informe a data!");
		}
		if(data.getLiturgia() == null){
			throw new InvalidEntityException("Informe a liturgia do dia!");
		}
	}

    @Transactional
    public void delete(Date date) {
        dataRepository.delete(date);
    }

}
