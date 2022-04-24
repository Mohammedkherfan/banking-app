package com.baraka.banking.test;

import com.baraka.banking.LaunchApplication;
import com.baraka.banking.jpa.AccountJpaRepository;
import com.baraka.banking.jpa.CustomerJpaRepository;
import com.baraka.banking.jpa.TransactionJpaRepository;
import com.baraka.banking.jpa.TransferJpaRepository;
import com.baraka.banking.request.BankingRequest;
import com.baraka.banking.response.BankingResponse;
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

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {LaunchApplication.class})
@AutoConfigureMockMvc
public class MoneyWithdrawalTest {

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

    private BankingRequest moneyWithdrawalRequest;
    private BankingRequest createAccountRequest;
    private BankingRequest deleteRequest;

    @Before
    public void setUp() throws Exception {
        Map<String, Object> moneyWithdrawalPayload = new HashMap<>();
        moneyWithdrawalPayload.put("operation", "money_withdrawal");
        moneyWithdrawalPayload.put("accountNumber", "06983836515000");
        moneyWithdrawalPayload.put("transactionAmount", new BigDecimal(100.00));
        moneyWithdrawalRequest = BankingRequest.builder()
                .request(moneyWithdrawalPayload)
                .build();

        Map<String, Object> deleteAccountPayload = new HashMap<>();
        deleteAccountPayload.put("operation", "delete_account");
        deleteAccountPayload.put("accountNumber", "06983836515000");
        deleteRequest = BankingRequest.builder()
                .request(deleteAccountPayload)
                .build();

        Map<String, Object> createAccountPayload = new HashMap<>();
        createAccountPayload.put("operation", "create_account");
        createAccountPayload.put("customerName", "Mohammed Kherfan");
        createAccountPayload.put("customerBirthDate", "1990-05-05");
        createAccountPayload.put("customerAddress", "Dubai, Paradise view 1, 904");
        createAccountPayload.put("customerPhone", "+971564110447");
        createAccountPayload.put("customerEmail", "mohammed.kherfan@gmail.com");
        createAccountPayload.put("accountType", "CURRENT_ACCOUNT");
        createAccountPayload.put("accountBranch", "Downtown");
        createAccountPayload.put("accountCurrency", "AED");
        createAccountRequest = BankingRequest.builder()
                .request(createAccountPayload)
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
    public void whenSendMoneyWithdrawal_AndPayloadIsNull_ThenShouldReturnError() throws Exception {
        moneyWithdrawalRequest.setRequest(null);
        String request = JacksonUtil.toJson(moneyWithdrawalRequest);

        ResultActions action = mvc.perform(post("/bank")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON));

        action
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", is("Error in request : Invalid request payload")));
    }

    @Test
    public void whenSendMoneyWithdrawal_AndPayloadIsEmpty_ThenShouldReturnError() throws Exception {
        moneyWithdrawalRequest.setRequest(new HashMap<>());
        String request = JacksonUtil.toJson(moneyWithdrawalRequest);

        ResultActions action = mvc.perform(post("/bank")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON));

        action
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", is("Error in request : Invalid request payload")));
    }

    @Test
    public void whenSendMoneyWithdrawal_AndOperationNotExist_ThenShouldReturnError() throws Exception {
        moneyWithdrawalRequest.getRequest().remove("operation");
        String request = JacksonUtil.toJson(moneyWithdrawalRequest);

        ResultActions action = mvc.perform(post("/bank")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON));

        action
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", is("Invalid operation")));
    }

    @Test
    public void whenSendMoneyWithdrawal_AndOperationIsInvalid_ThenShouldReturnError() throws Exception {
        moneyWithdrawalRequest.getRequest().put("operation", "anyOperation");
        String request = JacksonUtil.toJson(moneyWithdrawalRequest);

        ResultActions action = mvc.perform(post("/bank")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON));

        action
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", is("Invalid operation anyOperation")));
    }

    @Test
    public void whenSendMoneyWithdrawal_AndWithExistParameter_ThenShouldReturnError() throws Exception {
        moneyWithdrawalRequest.getRequest().put("name", "anyName");
        String request = JacksonUtil.toJson(moneyWithdrawalRequest);

        ResultActions action = mvc.perform(post("/bank")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON));

        action
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", is("[name] extra parameters")));
    }

    @Test
    public void whenSendMoneyWithdrawal_AndWithInvalidParameterFormat_ThenShouldReturnError() throws Exception {
        moneyWithdrawalRequest.getRequest().put("accountNumber", "anyFormat");
        String request = JacksonUtil.toJson(moneyWithdrawalRequest);

        ResultActions action = mvc.perform(post("/bank")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON));

        action
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", is("[accountNumber] invalid parameters format")));
    }

    @Test
    public void whenSendMoneyWithdrawal_AndWithMissingRequiredParameter_ThenShouldReturnError() throws Exception {
        moneyWithdrawalRequest.getRequest().remove("accountNumber");
        String request = JacksonUtil.toJson(moneyWithdrawalRequest);

        ResultActions action = mvc.perform(post("/bank")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON));

        action
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", is("[accountNumber] missing required parameter")));
    }

    @Test
    public void whenSendMoneyWithdrawal_AndAccountNumberNotExist_ThenShouldReturnError() throws Exception {
        String request = JacksonUtil.toJson(moneyWithdrawalRequest);

        ResultActions action = mvc.perform(post("/bank")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON));

        action
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", is("Account 06983836515000 not exist")));
    }

    @Test
    public void whenSendMoneyWithdrawal_AndAccountIsDeleted_ThenShouldReturnError() throws Exception {
        String request1 = JacksonUtil.toJson(createAccountRequest);

        BankingResponse response = JacksonUtil.fromJson(mvc.perform(post("/bank")
                        .content(request1)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andReturn()
                        .getResponse()
                        .getContentAsString(), BankingResponse.class);
        Map<String, Object> responseMap = (Map) response.getResponse();

        deleteRequest.getRequest().put("operation", "delete_account");
        deleteRequest.getRequest().put("accountNumber", responseMap.get("accountNumber"));
        String request2 = JacksonUtil.toJson(deleteRequest);
        mvc.perform(post("/bank")
                .content(request2)
                .contentType(MediaType.APPLICATION_JSON));


        moneyWithdrawalRequest.getRequest().put("accountNumber", responseMap.get("accountNumber"));
        String request3 = JacksonUtil.toJson(moneyWithdrawalRequest);

        ResultActions action3 = mvc.perform(post("/bank")
                .content(request3)
                .contentType(MediaType.APPLICATION_JSON));

        action3
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", is("Account not active DELETED ")));
    }

    @Test
    public void whenSendMoneyWithdrawal_AndNoMoneyInAccount_ThenShouldReturnError() throws Exception {
        String request1 = JacksonUtil.toJson(createAccountRequest);

        BankingResponse response = JacksonUtil.fromJson(mvc.perform(post("/bank")
                        .content(request1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString(), BankingResponse.class);
        Map<String, Object> responseMap = (Map) response.getResponse();

        moneyWithdrawalRequest.getRequest().put("accountNumber", responseMap.get("accountNumber"));
        String request3 = JacksonUtil.toJson(moneyWithdrawalRequest);

        ResultActions action3 = mvc.perform(post("/bank")
                .content(request3)
                .contentType(MediaType.APPLICATION_JSON));

        action3
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", is("Insufficient fund ")));
    }

    @Test
    public void whenSendMoneyWithdrawal_WithValidRequest_ThenShouldReturnSuccess() throws Exception {
        String request1 = JacksonUtil.toJson(createAccountRequest);

        BankingResponse response = JacksonUtil.fromJson(mvc.perform(post("/bank")
                        .content(request1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString(), BankingResponse.class);
        Map<String, Object> responseMap = (Map) response.getResponse();

        deleteRequest.getRequest().put("operation", "money_deposit");
        deleteRequest.getRequest().put("accountNumber", responseMap.get("accountNumber"));
        deleteRequest.getRequest().put("transactionAmount", new BigDecimal(1000));
        String request2 = JacksonUtil.toJson(deleteRequest);
        mvc.perform(post("/bank")
                .content(request2)
                .contentType(MediaType.APPLICATION_JSON));


        moneyWithdrawalRequest.getRequest().put("accountNumber", responseMap.get("accountNumber"));
        String request3 = JacksonUtil.toJson(moneyWithdrawalRequest);

        ResultActions action3 = mvc.perform(post("/bank")
                .content(request3)
                .contentType(MediaType.APPLICATION_JSON));

        action3
                .andDo(print())
                .andExpect(status().isCreated());
    }
}
