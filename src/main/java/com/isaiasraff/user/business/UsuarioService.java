package com.isaiasraff.user.business;

import com.isaiasraff.user.business.converter.UsuarioConverter;
import com.isaiasraff.user.business.dto.EnderecoDTO;
import com.isaiasraff.user.business.dto.TelefoneDTO;
import com.isaiasraff.user.business.dto.UsuarioDTO;
import com.isaiasraff.user.infraestructure.entity.Endereco;
import com.isaiasraff.user.infraestructure.entity.Telefone;
import com.isaiasraff.user.infraestructure.entity.Usuario;
import com.isaiasraff.user.infraestructure.exceptions.ConflictExceptions;
import com.isaiasraff.user.infraestructure.exceptions.ResourceNotFoundException;
import com.isaiasraff.user.infraestructure.repository.EnderecoRepository;
import com.isaiasraff.user.infraestructure.repository.TelefoneRepository;
import com.isaiasraff.user.infraestructure.repository.UsuarioRepository;
import com.isaiasraff.user.infraestructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final EnderecoRepository enderecoRepository;
    private final TelefoneRepository telefoneRepository;

    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO) {
        emailExiste(usuarioDTO.getEmail());
        usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
        usuario = usuarioRepository.save(usuario);
        return usuarioConverter.paraUsuarioDTO(usuario);
    }

    public void emailExiste(String email) {
        try {
            boolean existe = verificarEmailExistente(email);
            if (existe) {
                throw new ConflictExceptions("Email já cadastrado" + email);
            }
        } catch (ConflictExceptions e) {
            throw new ConflictExceptions("Email já cadastrado" + e.getCause());
        }

    }

    public boolean verificarEmailExistente(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    public UsuarioDTO buscarUsuarioEmail(String email) {
        try {
        return usuarioConverter.paraUsuarioDTO(
                usuarioRepository.findByEmail(email)
                        .orElseThrow(
                () -> new ResourceNotFoundException("Email não encontrado " + email)
                        )
        );
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("email não encontrado " + email);
        }
    }

    public void deletaUsuarioEmail(String email) {
        usuarioRepository.deleteByEmail(email);
    }

    public UsuarioDTO atualizaDadosUsuario(String token, UsuarioDTO dto) {
        String email = jwtUtil.extractUsername(token.substring(7));

        dto.setSenha(dto.getSenha() != null ? passwordEncoder.encode(dto.getSenha()) : null);

        Usuario usuarioEntity = (Usuario) usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Email não localizado"));

        Usuario usuario = usuarioConverter.updateUsuario(dto, usuarioEntity);

        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));

    }

    public EnderecoDTO atualizaEndereco (Long idEndereco, EnderecoDTO enderecoDTO) {
        Endereco entity = enderecoRepository.findById(idEndereco).orElseThrow(
                () -> new ResourceNotFoundException("Id não encontrado. " + idEndereco));

        Endereco endereco = usuarioConverter.updateEndereco(enderecoDTO, entity);
        return usuarioConverter.paraEnderecoDTO(enderecoRepository.save(endereco));

    }

    public TelefoneDTO atualizaTelefone (Long idTelefone, TelefoneDTO dto) {
        Telefone entity = telefoneRepository.findById(idTelefone).orElseThrow(
                () -> new ResourceNotFoundException("Id não encontrado. " + idTelefone));

        Telefone telefone = usuarioConverter.updateTelefone(dto, entity);
        return usuarioConverter.paraTelefoneDTO(telefoneRepository.save(telefone));


    }

    public EnderecoDTO cadastraEndereco (String token, EnderecoDTO dto) {
        String email = jwtUtil.extractUsername(token.substring(7));
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Email não localizado " + email));

        Endereco endereco = usuarioConverter.paraEnderecoEntity(dto, usuario.getId());
        Endereco enderecoEntity = enderecoRepository.save(endereco);
        return usuarioConverter.paraEnderecoDTO(enderecoEntity);

    }

    public TelefoneDTO cadastraTelefone (String token, TelefoneDTO dto) {
        String email = jwtUtil.extractUsername(token.substring(7));
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Email não localizado " + email));

        Telefone telefone = usuarioConverter.paraTelefoneEntity(dto, usuario.getId());
        return usuarioConverter.paraTelefoneDTO(
                telefoneRepository.save(telefone)
        );

    }


}
