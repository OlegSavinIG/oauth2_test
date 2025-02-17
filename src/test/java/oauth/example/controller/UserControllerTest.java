package oauth.example.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OAuth2User oAuth2User;

    @Test
    @WithMockUser
    public void testUserProfilePage() throws Exception {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("name", "John Doe");
        attributes.put("email", "john@example.com");

        when(oAuth2User.getAttributes()).thenReturn(attributes);
        when(oAuth2User.getAttribute("name")).thenReturn("John Doe");
        when(oAuth2User.getAttribute("email")).thenReturn("john@example.com");

        Authentication authentication = new OAuth2AuthenticationToken(
                oAuth2User,
                AuthorityUtils.createAuthorityList("ROLE_USER"),
                "google"
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        mockMvc.perform(get("/user"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("name", "John Doe"))
                .andExpect(model().attribute("email", "john@example.com"));
    }
}