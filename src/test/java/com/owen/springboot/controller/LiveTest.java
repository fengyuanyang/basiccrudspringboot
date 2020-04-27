package com.owen.springboot.controller;

import com.owen.springboot.SpringbootApplication;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.core.Is.isA;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringbootApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LiveTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;
    private final String allCompanies = "[{'id':10,'name':'tomato','address':'house','createdBy':'john','createdAt':'2020-04-26T10:47:52.690+0000','updatedBy':'owen','updatedAt':'2020-04-26T12:47:52.690+0000'}," +
            "{'id':11,'name':'banana','address':'zoo','createdBy':'tom','createdAt':'2020-04-26T10:47:52.690+0000','updatedBy':'john','updatedAt':'2020-04-26T10:47:52.690+0000'}," +
            "{'id':12,'name':'apple','address':'flower','createdBy':'john','createdAt':'2020-04-26T10:47:52.690+0000','updatedBy':'owen','updatedAt':'2020-04-26T12:47:52.690+0000'}," +
            "{'id':13,'name':'cucumber','address':'taipei','createdBy':'tom','createdAt':'2020-04-26T10:47:52.690+0000','updatedBy':'john','updatedAt':'2020-04-26T10:47:52.690+0000'}]";

    private final String allClients = "[{'id':14,'company':{'id':10,'name':'tomato','address':'house','createdBy':'john','createdAt':'2020-04-26T10:47:52.690+0000','updatedBy':'owen','updatedAt':'2020-04-26T12:47:52.690+0000'},'name':'tomato','email':'john@gmail.com','phone':'0912123456','createdBy':'john','createdAt':'2020-04-26T10:47:52.690+0000','updatedBy':'owen','updatedAt':'2020-04-26T12:47:52.690+0000'}," +
            "{'id':15,'company':{'id':11,'name':'banana','address':'zoo','createdBy':'tom','createdAt':'2020-04-26T10:47:52.690+0000','updatedBy':'john','updatedAt':'2020-04-26T10:47:52.690+0000'},'name':'banana','email':'tom@gmail.com','phone':'0912123456','createdBy':'tom','createdAt':'2020-04-26T10:47:52.690+0000','updatedBy':'john','updatedAt':'2020-04-26T10:47:52.690+0000'}]";
//

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).dispatchOptions(true).build();
    }

    @Test
    @WithMockUser(roles = "OTHERS")
    public void givenUser_1_IsOthers_whenGetCompanies_thenThrowAccessDeniedException() throws Exception {
        expectedException.expectCause(isA(AccessDeniedException.class));
        mockMvc.perform(get("/company/"));
    }

    @Test
    @WithMockUser(roles = "MANAGER")
    public void givenUser_1_IsManager_whenGetCompanies_thenAllCompanies() throws Exception {
        mockMvc.perform(get("/company/")).andExpect(status().isOk()).andExpect(content().json(allCompanies));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void givenUser_1_IsAdmin_whenGetCompanies_thenAllCompanies() throws Exception {
        mockMvc.perform(get("/company/")).andExpect(status().isOk()).andExpect(content().json(allCompanies));
    }

    @Test
    @WithMockUser(roles = "OTHERS")
    public void givenUser_2_IsOthers_whenModifyCompanies_thenThrowAccessDeniedException() throws Exception {
        String modifyCompanies = "{\"name\":\"tomato2\",\"address\":\"house2\"}";
        expectedException.expectCause(isA(AccessDeniedException.class));
        mockMvc.perform(put("/company/10").contentType(APPLICATION_JSON).content(modifyCompanies));
    }

    @Test
    @WithMockUser(roles = "OPERATOR")
    public void givenUser_2_IsOperator_whenModifyCompanies_thenThrowAccessDeniedException() throws Exception {
        String modifyCompanies = "{\"name\":\"tomato2\",\"address\":\"house2\"}";
        expectedException.expectCause(isA(AccessDeniedException.class));
        mockMvc.perform(put("/company/10").contentType(APPLICATION_JSON)
                .content(modifyCompanies)).andExpect(status().isOk()).andExpect(content().json(modifyCompanies));
    }

    @Test
    @WithMockUser(roles = "MANAGER", username = "try")
    public void givenUser_2_IsManager_whenModifyCompanies_thenReturnModifiedCompany() throws Exception {
        String modifyCompanies = "{\"name\":\"tomato2\",\"address\":\"house2\",\"updatedBy\":\"try\" }";
        mockMvc.perform(put("/company/10").contentType(APPLICATION_JSON)
                .content(modifyCompanies)).andExpect(status().isOk()).andExpect(content().json(modifyCompanies));
    }

    @Test
    @WithMockUser(roles = "ADMIN", username = "try2")
    public void givenUser_2_IsAdmin_whenModifyCompanies_thenReturnModifiedCompany() throws Exception {
        String modifyCompanies = "{\"name\":\"tomato3\",\"address\":\"house3\",\"updatedBy\":\"try2\" }";
        mockMvc.perform(put("/company/10").contentType(APPLICATION_JSON)
                .content(modifyCompanies)).andExpect(status().isOk()).andExpect(content().json(modifyCompanies));
    }

    @Test
    @WithMockUser(roles = "OTHERS")
    public void givenUser_3_IsOthers_whenDeleteCompanies_thenThrowAccessDeniedException() throws Exception {
        expectedException.expectCause(isA(AccessDeniedException.class));
        mockMvc.perform(delete("/company/10")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "OPERATOR")
    public void givenUser_3_IsOperator_whenDeleteCompanies_thenThrowAccessDeniedException() throws Exception {
        expectedException.expectCause(isA(AccessDeniedException.class));
        mockMvc.perform(delete("/company/10")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "MANAGER")
    public void givenUser_3_IsManager_whenDeleteCompanies_thenReturnInternalServerError() throws Exception {
        mockMvc.perform(delete("/company/10")).andExpect(status().isInternalServerError());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void givenUser_3_IsAdmin_whenDeleteCompanies_thenReturnInternalServerError() throws Exception {
        mockMvc.perform(delete("/company/11")).andExpect(status().isInternalServerError());
    }

    @Test
    @WithMockUser(roles = "MANAGER")
    public void givenUser_3_IsManager_whenDeleteCompanies_thenReturnModifiedCompany() throws Exception {
        mockMvc.perform(delete("/company/12")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void givenUser_3_IsAdmin_whenDeleteCompanies_thenReturnModifiedCompany() throws Exception {
        mockMvc.perform(delete("/company/13")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "OTHERS")
    public void givenUser_4_IsOthers_whenAddCompanies_thenThrowAccessDeniedException() throws Exception {
        String addCompanies = "[{\"name\":\"tomato5\",\"address\":\"house5\",\"updatedBy\":\"try2\",\"createdBy\":\"try2\" }," +
                "{\"name\":\"tomato4\",\"address\":\"house4\",\"updatedBy\":\"try2\",\"createdBy\":\"try2\" }]";
        expectedException.expectCause(isA(AccessDeniedException.class));
        mockMvc.perform(post("/company/").contentType(APPLICATION_JSON)
                .content(addCompanies)).andExpect(status().isOk()).andExpect(content().json(addCompanies));
    }

    @Test
    @WithMockUser(roles = "MANAGER")
    public void givenUser_4_IsManager_whenAddCompanies_thenThrowAccessDeniedException() throws Exception {
        String addCompanies = "[{\"name\":\"tomato5\",\"address\":\"house5\",\"updatedBy\":\"try2\",\"createdBy\":\"try2\" }," +
                "{\"name\":\"tomato4\",\"address\":\"house4\",\"updatedBy\":\"try2\",\"createdBy\":\"try2\" }]";
        expectedException.expectCause(isA(AccessDeniedException.class));
        mockMvc.perform(post("/company/").contentType(APPLICATION_JSON)
                .content(addCompanies)).andExpect(status().isOk()).andExpect(content().json(addCompanies));
    }

    @Test
    @WithMockUser(roles = "OPERATOR", username = "try")
    public void givenUser_4_IsOperator_whenAddCompanies_thenReturnAddedCompanies() throws Exception {
        String addCompanies = "[{\"name\":\"tomato5\",\"address\":\"house5\",\"updatedBy\":\"try\",\"createdBy\":\"try\" }," +
                "{\"name\":\"tomato4\",\"address\":\"house4\",\"updatedBy\":\"try\",\"createdBy\":\"try\" }]";
        mockMvc.perform(post("/company/").contentType(APPLICATION_JSON)
                .content(addCompanies)).andExpect(status().isOk()).andExpect(content().json(addCompanies));
    }

    @Test
    @WithMockUser(roles = "ADMIN", username = "try2")
    public void givenUser_4_IsAdmin_whenAddCompanies_thenReturnAddedCompanies() throws Exception {
        String addCompanies = "[{\"name\":\"tomato6\",\"address\":\"house5\",\"updatedBy\":\"try2\",\"createdBy\":\"try2\" }," +
                "{\"name\":\"tomato7\",\"address\":\"house4\",\"updatedBy\":\"try2\",\"createdBy\":\"try2\" }]";
        mockMvc.perform(post("/company/").contentType(APPLICATION_JSON)
                .content(addCompanies)).andExpect(status().isOk()).andExpect(content().json(addCompanies));
    }




















    @Test
    @WithMockUser(roles = "OTHERS")
    public void givenUser_1_IsOthers_whenGetClients_thenThrowAccessDeniedException() throws Exception {
        expectedException.expectCause(isA(AccessDeniedException.class));
        mockMvc.perform(get("/client/"));
    }

    @Test
    @WithMockUser(roles = "OPERATOR")
    public void givenUser_1_IsOperator_whenGetCompanies_thenAllCompanies() throws Exception {
        mockMvc.perform(get("/client/")).andExpect(status().isOk()).andExpect(content().json(allClients));
    }

    @Test
    @WithMockUser(roles = "MANAGER")
    public void givenUser_1_IsManager_whenGetClients_thenAllClients() throws Exception {
        mockMvc.perform(get("/client/")).andExpect(status().isOk()).andExpect(content().json(allClients));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void givenUser_1_IsAdmin_whenGetClients_thenAllClients() throws Exception {
        mockMvc.perform(get("/client/")).andExpect(status().isOk()).andExpect(content().json(allClients));
    }

    @Test
    @WithMockUser(roles = "OTHERS")
    public void givenUser_2_IsOthers_whenModifyClients_thenThrowAccessDeniedException() throws Exception {
        String modifyClients = "{\"name\":\"tomato2\",\"email\":\"john2@gmail.com\",\"phone\":\"09121234562\"}";
        expectedException.expectCause(isA(AccessDeniedException.class));
        mockMvc.perform(put("/client/14").contentType(APPLICATION_JSON).content(modifyClients));
    }

    @Test
    @WithMockUser(roles = "OPERATOR")
    public void givenUser_2_IsOperator_whenModifyClients_thenThrowAccessDeniedException() throws Exception {
        String modifyClients = "{\"name\":\"tomato2\",\"email\":\"john2@gmail.com\",\"phone\":\"09121234562\"}";
        expectedException.expectCause(isA(AccessDeniedException.class));
        mockMvc.perform(put("/client/14").contentType(APPLICATION_JSON)
                .content(modifyClients)).andExpect(status().isOk()).andExpect(content().json(modifyClients));
    }

    @Test
    @WithMockUser(roles = "MANAGER")
    public void givenUser_2_IsManager_whenModifyClients_thenReturnModifiedClient() throws Exception {
        String modifyClients = "{\"name\":\"tomato3\",\"email\":\"john2@gmail.com\",\"phone\":\"09121234562\"}";
        mockMvc.perform(put("/client/14").contentType(APPLICATION_JSON)
                .content(modifyClients)).andExpect(status().isOk()).andExpect(content().json(modifyClients));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void givenUser_2_IsAdmin_whenModifyClients_thenReturnModifiedClient() throws Exception {
        String modifyClients = "{\"name\":\"tomato4\",\"email\":\"john2@gmail.com\",\"phone\":\"09121234562\"}";
        mockMvc.perform(put("/client/14").contentType(APPLICATION_JSON)
                .content(modifyClients)).andExpect(status().isOk()).andExpect(content().json(modifyClients));
    }

    @Test
    @WithMockUser(roles = "OTHERS")
    public void givenUser_3_IsOthers_whenDeleteClients_thenThrowAccessDeniedException() throws Exception {
        expectedException.expectCause(isA(AccessDeniedException.class));
        mockMvc.perform(delete("/client/14")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "OPERATOR")
    public void givenUser_3_IsOperator_whenDeleteClients_thenThrowAccessDeniedException() throws Exception {
        expectedException.expectCause(isA(AccessDeniedException.class));
        mockMvc.perform(delete("/client/14")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "MANAGER")
    public void givenUser_3_IsManager_whenDeleteClients_thenReturnModifiedClient() throws Exception {
        mockMvc.perform(delete("/client/14")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void givenUser_3_IsAdmin_whenDeleteClients_thenReturnModifiedClient() throws Exception {
        mockMvc.perform(delete("/client/6")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "OTHERS")
    public void givenUser_4_IsOthers_whenAddClients_thenThrowAccessDeniedException() throws Exception {
        String addClients = "[{\"name\":\"tomato5\",\"email\":\"json@hotmail.com\",\"phone\":\"0912121212\",\"updatedBy\":\"try\",\"createdBy\":\"try\" }," +
                "{\"name\":\"tomato8\",\"email\":\"json@hotmail.com\",\"phone\":\"09121212124\",\"updatedBy\":\"try\",\"createdBy\":\"try\" }]";
        expectedException.expectCause(isA(AccessDeniedException.class));
        mockMvc.perform(post("/client/").contentType(APPLICATION_JSON)
                .content(addClients)).andExpect(status().isOk()).andExpect(content().json(addClients));
    }

    @Test
    @WithMockUser(roles = "MANAGER")
    public void givenUser_4_IsManager_whenAddClients_thenThrowAccessDeniedException() throws Exception {
        String addClients = "[{\"name\":\"tomato5\",\"email\":\"json@hotmail.com\",\"phone\":\"0912121212\",\"updatedBy\":\"try\",\"createdBy\":\"try\" }," +
                "{\"name\":\"tomato8\",\"email\":\"json@hotmail.com\",\"phone\":\"09121212124\",\"updatedBy\":\"try\",\"createdBy\":\"try\" }]";
        expectedException.expectCause(isA(AccessDeniedException.class));
        mockMvc.perform(post("/client/").contentType(APPLICATION_JSON)
                .content(addClients)).andExpect(status().isOk()).andExpect(content().json(addClients));
    }

    @Test
    @WithMockUser(roles = "OPERATOR", username = "try")
    public void givenUser_4_IsOperator_whenAddClients_thenReturnAddedClients() throws Exception {
        String addClients = "[{\"name\":\"tomato5\",\"email\":\"json@hotmail.com\",\"phone\":\"0912121212\",\"updatedBy\":\"try\",\"createdBy\":\"try\" }," +
                "{\"name\":\"tomato8\",\"email\":\"json@hotmail.com\",\"phone\":\"09121212124\",\"updatedBy\":\"try\",\"createdBy\":\"try\" }]";
        mockMvc.perform(post("/client/").contentType(APPLICATION_JSON)
                .content(addClients)).andExpect(status().isOk()).andExpect(content().json(addClients));
    }

    @Test
    @WithMockUser(roles = "ADMIN", username = "try2")
    public void givenUser_4_IsAdmin_whenAddClients_thenReturnAddedClients() throws Exception {
        String addClients = "[{\"name\":\"tomato5\",\"email\":\"json@hotmail.com\",\"phone\":\"0912121212\",\"updatedBy\":\"try2\",\"createdBy\":\"try2\" }," +
                "{\"name\":\"tomato8\",\"email\":\"json@hotmail.com\",\"phone\":\"09121212124\",\"updatedBy\":\"try2\",\"createdBy\":\"try2\" }]";
        mockMvc.perform(post("/client/").contentType(APPLICATION_JSON)
                .content(addClients)).andExpect(status().isOk()).andExpect(content().json(addClients));
    }
}
