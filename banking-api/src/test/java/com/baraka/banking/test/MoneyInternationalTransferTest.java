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
public class MoneyInternationalTransferTest {

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

    private BankingRequest moneyInternationalTransferRequest;
    private BankingRequest createAccountRequest;
    private BankingRequest deleteRequest;
    private BankingRequest moneyDepositRequest;

    @Before
    public void setUp() throws Exception {
        Map<String, Object> moneyDepositPayload = new HashMap<>();
        moneyDepositPayload.put("operation", "money_deposit");
        moneyDepositPayload.put("accountNumber", "06983836515000");
        moneyDepositPayload.put("transactionAmount", new BigDecimal(100.00));
        moneyDepositRequest = BankingRequest.builder()
                .request(moneyDepositPayload)
                .build();

        Map<String, Object> moneyInternationalTransferPayload = new HashMap<>();
        moneyInternationalTransferPayload.put("operation", "money_international_transfer");
        moneyInternationalTransferPayload.put("accountNumber", "06983836515000");
        moneyInternationalTransferPayload.put("payAccountNumber", "06983836515001");
        moneyInternationalTransferPayload.put("payIban", "AE3002600006983836515001");
        moneyInternationalTransferPayload.put("payBank", "ENBD");
        moneyInternationalTransferPayload.put("payBranch", "Business Bay");
        moneyInternationalTransferPayload.put("transferAmount", new BigDecimal(100.00));
        moneyInternationalTransferRequest = BankingRequest.builder()
                .request(moneyInternationalTransferPayload)
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
    public void whenSendMoneyInternationalTransfer_AndPayloadIsNull_ThenShouldReturnError() throws Exception {
        moneyInternationalTransferRequest.setRequest(null);
        String request = JacksonUtil.toJson(moneyInternationalTransferRequest);

        ResultActions action = mvc.perform(post("/bank")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON));

        action
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", is("Error in request : Invalid request payload")));
    }

    @Test
    public void whenSendMoneyInternationalTransfer_AndPayloadIsEmpty_ThenShouldReturnError() throws Exception {
        moneyInternationalTransferRequest.setRequest(new HashMap<>());
        String request = JacksonUtil.toJson(moneyInternationalTransferRequest);

        ResultActions action = mvc.perform(post("/bank")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON));

        action
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", is("Error in request : Invalid request payload")));
    }

    @Test
    public void whenSendMoneyInternationalTransfer_AndOperationNotExist_ThenShouldReturnError() throws Exception {
        moneyInternationalTransferRequest.getRequest().remove("operation");
        String request = JacksonUtil.toJson(moneyInternationalTransferRequest);

        ResultActions action = mvc.perform(post("/bank")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON));

        action
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", is("Invalid operation")));
    }

    @Test
    public void whenSendMoneyInternationalTransfer_AndOperationIsInvalid_ThenShouldReturnError() throws Exception {
        moneyInternationalTransferRequest.getRequest().put("operation", "anyOperation");
        String request = JacksonUtil.toJson(moneyInternationalTransferRequest);

        ResultActions action = mvc.perform(post("/bank")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON));

        action
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", is("Invalid operation anyOperation")));
    }

    @Test
    public void whenSendMoneyInternationalTransfer_AndWithExtraParameter_ThenShouldReturnError() throws Exception {
        moneyInternationalTransferRequest.getRequest().put("name", "anyName");
        String request = JacksonUtil.toJson(moneyInternationalTransferRequest);

        ResultActions action = mvc.perform(post("/bank")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON));

        action
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", is("[name] extra parameters")));
    }

    @Test
    public void whenSendMoneyInternationalTransfer_AndWithInvalidParameterFormat_ThenShouldReturnError() throws Exception {
        moneyInternationalTransferRequest.getRequest().put("accountNumber", "anyFormat");
        String request = JacksonUtil.toJson(moneyInternationalTransferRequest);

        ResultActions action = mvc.perform(post("/bank")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON));

        action
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", is("[accountNumber] invalid parameters format")));
    }

    @Test
    public void whenSendMoneyInternationalTransfer_AndWithMissingRequiredParameter_ThenShouldReturnError() throws Exception {
        moneyInternationalTransferRequest.getRequest().remove("accountNumber");
        String request = JacksonUtil.toJson(moneyInternationalTransferRequest);

        ResultActions action = mvc.perform(post("/bank")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON));

        action
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", is("[accountNumber] missing required parameter")));
    }

    @Test
    public void whenSendMoneyInternationalTransfer_AndBfdAccountNumberNotExist_ThenShouldReturnError() throws Exception {
        String request = JacksonUtil.toJson(moneyInternationalTransferRequest);

        ResultActions action = mvc.perform(post("/bank")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON));

        action
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", is("Account 06983836515000 not exist")));
    }

    @Test
    public void whenSendMoneyInternationalTransfer_AndBfdAccountIsDeleted_ThenShouldReturnError() throws Exception {
        String request1 = JacksonUtil.toJson(createAccountRequest);

        BankingResponse response1 = JacksonUtil.fromJson(mvc.perform(post("/bank")
                        .content(request1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString(), BankingResponse.class);
        Map<String, Object> responseMap1 = (Map) response1.getResponse();

        moneyInternationalTransferRequest.getRequest().put("accountNumber", responseMap1.get("accountNumber"));


        deleteRequest.getRequest().put("operation", "delete_account");
        deleteRequest.getRequest().put("accountNumber", responseMap1.get("accountNumber"));
        String request3 = JacksonUtil.toJson(deleteRequest);
        mvc.perform(post("/bank")
                .content(request3)
                .contentType(MediaType.APPLICATION_JSON));

        String request4 = JacksonUtil.toJson(moneyInternationalTransferRequest);

        ResultActions action4 = mvc.perform(post("/bank")
                .content(request4)
                .contentType(MediaType.APPLICATION_JSON));

        action4
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", is("Account not active DELETED ")));
    }


    @Test
    public void whenSendMoneyInternationalTransfer_AndNoMoneyInAccount_ThenShouldReturnError() throws Exception {
        String request1 = JacksonUtil.toJson(createAccountRequest);

        BankingResponse response1 = JacksonUtil.fromJson(mvc.perform(post("/bank")
                        .content(request1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString(), BankingResponse.class);
        Map<String, Object> responseMap1 = (Map) response1.getResponse();

        moneyInternationalTransferRequest.getRequest().put("accountNumber", responseMap1.get("accountNumber"));


        String request4 = JacksonUtil.toJson(moneyInternationalTransferRequest);

        ResultActions action4 = mvc.perform(post("/bank")
                .content(request4)
                .contentType(MediaType.APPLICATION_JSON));

        action4
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", is("Insufficient fund ")));
    }

    @Test
    public void whenSendMoneyInternationalTransfer_WithValidRequest_ThenShouldReturnSuccess() throws Exception {
        String request1 = JacksonUtil.toJson(createAccountRequest);

        BankingResponse response1 = JacksonUtil.fromJson(mvc.perform(post("/bank")
                        .content(request1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString(), BankingResponse.class);
        Map<String, Object> responseMap1 = (Map) response1.getResponse();

        moneyDepositRequest.getRequest().put("accountNumber", responseMap1.get("accountNumber"));
        String request3 = JacksonUtil.toJson(moneyDepositRequest);
        mvc.perform(post("/bank")
                .content(request3)
                .contentType(MediaType.APPLICATION_JSON));

        moneyInternationalTransferRequest.getRequest().put("accountNumber", responseMap1.get("accountNumber"));
        String request4 = JacksonUtil.toJson(moneyInternationalTransferRequest);

        ResultActions action4 = mvc.perform(post("/bank")
                .content(request4)
                .contentType(MediaType.APPLICATION_JSON));

        action4
                .andDo(print())
                .andExpect(status().isCreated());
    }
}
