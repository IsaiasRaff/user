package com.isaiasraff.user.business;

import com.isaiasraff.user.business.converter.UsuarioConverter;
import com.isaiasraff.user.business.dto.UsuarioDTO;
import com.isaiasraff.user.infraestructure.entity.Usuario;
import com.isaiasraff.user.infraestructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;

    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO){
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
        usuario = usuarioRepository.save(usuario);
        return usuarioConverter.paraUsuarioDTO(usuario);
    }

}
