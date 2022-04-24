package com.baraka.banking.test;

import com.baraka.banking.LaunchApplication;
import com.baraka.banking.jpa.AccountJpaRepository;
import com.baraka.banking.jpa.CustomerJpaRepository;
import com.baraka.banking.jpa.TransactionJpaRepository;
import com.baraka.banking.jpa.TransferJpaRepository;
import com.baraka.banking.request.BankingRequest;
import com.baraka.banking.util.JacksonUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {LaunchApplication.class})
@AutoConfigureMockMvc
public class CreateAccountTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private CustomerJpaRepository customerJpaRepository;
    @Autowired
    private AccountJpaRepository accountJpaRepository;
    @Autowired
    private TransactionJpaRepository transactionJpaRepository;
    @Autowired
    private TransferJpaRepository transferJpaRepository;

    private BankingRequest bankingRequest;

    @Before
    public void setUp() throws Exception {
        Map<String, Object> payload = new HashMap<>();
        payload.put("operation", "create_account");
        payload.put("customerName", "Mohammed Kherfan");
        payload.put("customerBirthDate", "1990-05-05");
        payload.put("customerAddress", "Dubai, Paradise view 1, 904");
        payload.put("customerPhone", "+971564110447");
        payload.put("customerEmail", "mohammed.kherfan@gmail.com");
        payload.put("accountType", "CURRENT_ACCOUNT");
        payload.put("accountBranch", "Downtown");
        payload.put("accountCurrency", "AED");
        bankingRequest = BankingRequest.builder()
                .request(payload)
                .build();
    }

    @After
    public void tearDown() throws Exception {
        transferJpaRepository.deleteAll();
        transactionJpaRepository.deleteAll();
        accountJpaRepository.deleteAll();
        customerJpaRepository.deleteAll();
    }

    @Test
    public void whenSendCreateAccount_AndPayloadIsNull_ThenShouldReturnError() throws Exception {
        bankingRequest.setRequest(null);
        String request = JacksonUtil.toJson(bankingRequest);

        ResultActions action = mvc.perform(post("/bank")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON));

        action
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", is("Error in request : Invalid request payload")));
    }

    @Test
    public void whenSendCreateAccount_AndPayloadIsEmpty_ThenShouldReturnError() throws Exception {
        bankingRequest.setRequest(new HashMap<>());
        String request = JacksonUtil.toJson(bankingRequest);

        ResultActions action = mvc.perform(post("/bank")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON));

        action
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", is("Error in request : Invalid request payload")));
    }

    @Test
    public void whenSendCreateAccount_AndOperationNotExist_ThenShouldReturnError() throws Exception {
        bankingRequest.getRequest().remove("operation");
        String request = JacksonUtil.toJson(bankingRequest);

        ResultActions action = mvc.perform(post("/bank")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON));

        action
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", is("Invalid operation")));
    }

    @Test
    public void whenSendCreateAccount_AndOperationIsInvalid_ThenShouldReturnError() throws Exception {
        bankingRequest.getRequest().put("operation", "anyOperation");
        String request = JacksonUtil.toJson(bankingRequest);

        ResultActions action = mvc.perform(post("/bank")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON));

        action
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", is("Invalid operation anyOperation")));
    }

    @Test
    public void whenSendCreateAccount_AndWithExistParameter_ThenShouldReturnError() throws Exception {
        bankingRequest.getRequest().put("name", "anyName");
        String request = JacksonUtil.toJson(bankingRequest);

        ResultActions action = mvc.perform(post("/bank")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON));

        action
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", is("[name] extra parameters")));
    }

    @Test
    public void whenSendCreateAccount_AndWithInvalidParameterFormat_ThenShouldReturnError() throws Exception {
        bankingRequest.getRequest().put("customerBirthDate", "anyFormat");
        String request = JacksonUtil.toJson(bankingRequest);

        ResultActions action = mvc.perform(post("/bank")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON));

        action
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", is("[customerBirthDate] invalid parameters format")));
    }

    @Test
    public void whenSendCreateAccount_AndWithMissingRequiredParameter_ThenShouldReturnError() throws Exception {
        bankingRequest.getRequest().remove("customerBirthDate");
        String request = JacksonUtil.toJson(bankingRequest);

        ResultActions action = mvc.perform(post("/bank")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON));

        action
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", is("[customerBirthDate] missing required parameter")));
    }

    @Test
    public void whenSendCreateAccount_AndValidRequest_ThenShouldReturnSuccess() throws Exception {
        String request = JacksonUtil.toJson(bankingRequest);

        ResultActions action = mvc.perform(post("/bank")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON));

        action
                .andDo(print())
                .andExpect(status().isCreated());
    }
}
