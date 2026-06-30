package br.uff.ic.integration;

import br.uff.ic.auth.model.Usuario;
import br.uff.ic.auth.repository.UsuarioRepository;
import br.uff.ic.auth.util.Role;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public abstract class IntegrationTestBase {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected UsuarioRepository usuarioRepository;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    protected Usuario criarUsuario(String nome, String email, String senha, Role role) {
        Usuario usuario = new Usuario(nome, email, passwordEncoder.encode(senha), role);
        return usuarioRepository.save(usuario);
    }

    protected String obterToken(String email, String senha) throws Exception {
        String body = objectMapper.writeValueAsString(
            java.util.Map.of("email", email, "senha", senha));

        MvcResult result = mockMvc.perform(post("/autenticacao/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
            .andExpect(status().isOk())
            .andReturn();

        JsonNode json = objectMapper.readTree(result.getResponse().getContentAsString());
        return json.get("token").asText();
    }
}
