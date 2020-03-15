package br.com.logic.pendotiba.logicbus.service;

import br.com.logic.pendotiba.core.repository.UsuarioRepository;


public enum StatusUsuario {
	
	ATIVAR {
		@Override
		public void executar(Long[] ids, UsuarioRepository usuarioRepository) {
			usuarioRepository.findByIdIn(ids).forEach(u -> u.setAtivo(true));
		}
	},
	
	DESATIVAR {
		@Override
		public void executar(Long[] ids, UsuarioRepository usuarioRepository) {
			usuarioRepository.findByIdIn(ids).forEach(u -> u.setAtivo(false));
		}
	};
	
	public abstract void executar(Long[] ids, UsuarioRepository usuarioRepository);

}
