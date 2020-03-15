package br.com.logic.pendotiba.logicbus.service;

import br.com.logic.pendotiba.core.model.DispositivoMovel;
import br.com.logic.pendotiba.core.model.HistoricoDispositivoMovel;
import br.com.logic.pendotiba.core.model.Usuario;
import br.com.logic.pendotiba.core.repository.DispositivoMovelRepository;
import br.com.logic.pendotiba.core.repository.HistoricoDispositivoMovelRepository;
import br.com.logic.pendotiba.logicbus.service.exception.ImpossivelExcluirEntidadeException;
import br.com.logic.pendotiba.logicbus.service.exception.NegocioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DispositivoMovelService {
	
	@Autowired
	DispositivoMovelRepository dispositivoMovelRepository;
	
	@Autowired
	HistoricoDispositivoMovelRepository historicoDispositivoMovelRepository;
	

	public void salvar(DispositivoMovel dispositivoMovel, Usuario usuario){
		Optional<DispositivoMovel> dispositivoMovelPersistido = dispositivoMovelRepository.findByImei(dispositivoMovel.getImei());
		if (dispositivoMovelPersistido.isPresent() && !dispositivoMovelPersistido.get().getId().equals(dispositivoMovel.getId()))
			throw new NegocioException("Já existe um dispositivo móvel com os dados informados");
		
		dispositivoMovel.setDataCadastro(new Date());
		dispositivoMovelRepository.save(dispositivoMovel);
		historicoDispositivoMovelRepository.save(new HistoricoDispositivoMovel(dispositivoMovel, usuario));
	}
	

	@Transactional
	public void excluir(DispositivoMovel dispositivoMovel){
		try {
			List<HistoricoDispositivoMovel> historico = historicoDispositivoMovelRepository.findByDispositivoMovel(dispositivoMovel);
			historico.forEach(h -> historicoDispositivoMovelRepository.delete(h));
			dispositivoMovelRepository.delete(dispositivoMovel);
		} catch (DataIntegrityViolationException e) {
			throw new ImpossivelExcluirEntidadeException("Existe registros cadastrados para o dispositivo móvel " + dispositivoMovel);
		}
	}
}
