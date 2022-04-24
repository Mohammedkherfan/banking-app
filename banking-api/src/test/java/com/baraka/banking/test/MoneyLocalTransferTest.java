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
public class MoneyLocalTransferTest {

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

    private BankingRequest moneyLocalTransferRequest;
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

        Map<String, Object> moneyLocalTransferPayload = new HashMap<>();
        moneyLocalTransferPayload.put("operation", "money_local_transfer");
        moneyLocalTransferPayload.put("accountNumber", "06983836515000");
        moneyLocalTransferPayload.put("payAccountNumber", "06983836515001");
        moneyLocalTransferPayload.put("transferAmount", new BigDecimal(100.00));
        moneyLocalTransferRequest = BankingRequest.builder()
                .request(moneyLocalTransferPayload)
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
    public void whenSendMoneyLocalTransfer_AndPayloadIsNull_ThenShouldReturnError() throws Exception {
        moneyLocalTransferRequest.setRequest(null);
        String request = JacksonUtil.toJson(moneyLocalTransferRequest);

        ResultActions action = mvc.perform(post("/bank")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON));

        action
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", is("Error in request : Invalid request payload")));
    }

    @Test
    public void whenSendMoneyLocalTransfer_AndPayloadIsEmpty_ThenShouldReturnError() throws Exception {
        moneyLocalTransferRequest.setRequest(new HashMap<>());
        String request = JacksonUtil.toJson(moneyLocalTransferRequest);

        ResultActions action = mvc.perform(post("/bank")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON));

        action
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", is("Error in request : Invalid request payload")));
    }

    @Test
    public void whenSendMoneyLocalTransfer_AndOperationNotExist_ThenShouldReturnError() throws Exception {
        moneyLocalTransferRequest.getRequest().remove("operation");
        String request = JacksonUtil.toJson(moneyLocalTransferRequest);

        ResultActions action = mvc.perform(post("/bank")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON));

        action
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", is("Invalid operation")));
    }

    @Test
    public void whenSendMoneyLocalTransfer_AndOperationIsInvalid_ThenShouldReturnError() throws Exception {
        moneyLocalTransferRequest.getRequest().put("operation", "anyOperation");
        String request = JacksonUtil.toJson(moneyLocalTransferRequest);

        ResultActions action = mvc.perform(post("/bank")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON));

        action
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", is("Invalid operation anyOperation")));
    }

    @Test
    public void whenSendMoneyLocalTransfer_AndWithExtraParameter_ThenShouldReturnError() throws Exception {
        moneyLocalTransferRequest.getRequest().put("name", "anyName");
        String request = JacksonUtil.toJson(moneyLocalTransferRequest);

        ResultActions action = mvc.perform(post("/bank")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON));

        action
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", is("[name] extra parameters")));
    }

    @Test
    public void whenSendMoneyLocalTransfer_AndWithInvalidParameterFormat_ThenShouldReturnError() throws Exception {
        moneyLocalTransferRequest.getRequest().put("accountNumber", "anyFormat");
        String request = JacksonUtil.toJson(moneyLocalTransferRequest);

        ResultActions action = mvc.perform(post("/bank")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON));

        action
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", is("[accountNumber] invalid parameters format")));
    }

    @Test
    public void whenSendMoneyLocalTransfer_AndWithMissingRequiredParameter_ThenShouldReturnError() throws Exception {
        moneyLocalTransferRequest.getRequest().remove("accountNumber");
        String request = JacksonUtil.toJson(moneyLocalTransferRequest);

        ResultActions action = mvc.perform(post("/bank")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON));

        action
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", is("[accountNumber] missing required parameter")));
    }

    @Test
    public void whenSendMoneyLocalTransfer_AndBfdAccountNumberNotExist_ThenShouldReturnError() throws Exception {
        String request = JacksonUtil.toJson(moneyLocalTransferRequest);

        ResultActions action = mvc.perform(post("/bank")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON));

        action
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", is("Account 06983836515000 not exist")));
    }

    @Test
    public void whenSendMoneyLocalTransfer_AndPayAccountNumberNotExist_ThenShouldReturnError() throws Exception {
        String request1 = JacksonUtil.toJson(createAccountRequest);

        BankingResponse response = JacksonUtil.fromJson(mvc.perform(post("/bank")
                        .content(request1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString(), BankingResponse.class);
        Map<String, Object> responseMap = (Map) response.getResponse();

        moneyLocalTransferRequest.getRequest().put("accountNumber", responseMap.get("accountNumber"));

        String request2 = JacksonUtil.toJson(moneyLocalTransferRequest);

        ResultActions action2 = mvc.perform(post("/bank")
                .content(request2)
                .contentType(MediaType.APPLICATION_JSON));

        action2
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", is("Account 06983836515001 not exist")));
    }

    @Test
    public void whenSendMoneyLocalTransfer_AndBfdAccountIsDeleted_ThenShouldReturnError() throws Exception {
        String request1 = JacksonUtil.toJson(createAccountRequest);

        BankingResponse response1 = JacksonUtil.fromJson(mvc.perform(post("/bank")
                        .content(request1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString(), BankingResponse.class);
        Map<String, Object> responseMap1 = (Map) response1.getResponse();

        moneyLocalTransferRequest.getRequest().put("accountNumber", responseMap1.get("accountNumber"));

        String request2 = JacksonUtil.toJson(createAccountRequest);

        BankingResponse response2 = JacksonUtil.fromJson(mvc.perform(post("/bank")
                        .content(request2)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString(), BankingResponse.class);
        Map<String, Object> responseMap2 = (Map) response2.getResponse();

        deleteRequest.getRequest().put("operation", "delete_account");
        deleteRequest.getRequest().put("accountNumber", responseMap1.get("accountNumber"));
        String request3 = JacksonUtil.toJson(deleteRequest);
        mvc.perform(post("/bank")
                .content(request3)
                .contentType(MediaType.APPLICATION_JSON));

        moneyLocalTransferRequest.getRequest().put("payAccountNumber", responseMap2.get("accountNumber"));
        String request4 = JacksonUtil.toJson(moneyLocalTransferRequest);

        ResultActions action4 = mvc.perform(post("/bank")
                .content(request4)
                .contentType(MediaType.APPLICATION_JSON));

        action4
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", is("Account not active DELETED ")));
    }

    @Test
    public void whenSendMoneyLocalTransfer_AndPayAccountIsDeleted_ThenShouldReturnError() throws Exception {
        String request1 = JacksonUtil.toJson(createAccountRequest);

        BankingResponse response1 = JacksonUtil.fromJson(mvc.perform(post("/bank")
                        .content(request1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString(), BankingResponse.class);
        Map<String, Object> responseMap1 = (Map) response1.getResponse();

        moneyLocalTransferRequest.getRequest().put("accountNumber", responseMap1.get("accountNumber"));

        String request2 = JacksonUtil.toJson(createAccountRequest);

        BankingResponse response2 = JacksonUtil.fromJson(mvc.perform(post("/bank")
                        .content(request2)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString(), BankingResponse.class);
        Map<String, Object> responseMap2 = (Map) response2.getResponse();

        deleteRequest.getRequest().put("operation", "delete_account");
        deleteRequest.getRequest().put("accountNumber", responseMap2.get("accountNumber"));
        String request3 = JacksonUtil.toJson(deleteRequest);
        mvc.perform(post("/bank")
                .content(request3)
                .contentType(MediaType.APPLICATION_JSON));

        moneyLocalTransferRequest.getRequest().put("payAccountNumber", responseMap2.get("accountNumber"));

        String request4 = JacksonUtil.toJson(moneyLocalTransferRequest);

        ResultActions action4 = mvc.perform(post("/bank")
                .content(request4)
                .contentType(MediaType.APPLICATION_JSON));

        action4
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", is("Account not active DELETED ")));
    }

    @Test
    public void whenSendMoneyLocalTransfer_AndNoMoneyInAccount_ThenShouldReturnError() throws Exception {
        String request1 = JacksonUtil.toJson(createAccountRequest);

        BankingResponse response1 = JacksonUtil.fromJson(mvc.perform(post("/bank")
                        .content(request1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString(), BankingResponse.class);
        Map<String, Object> responseMap1 = (Map) response1.getResponse();

        moneyLocalTransferRequest.getRequest().put("accountNumber", responseMap1.get("accountNumber"));

        String request2 = JacksonUtil.toJson(createAccountRequest);

        BankingResponse response2 = JacksonUtil.fromJson(mvc.perform(post("/bank")
                        .content(request2)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString(), BankingResponse.class);
        Map<String, Object> responseMap2 = (Map) response2.getResponse();

        moneyLocalTransferRequest.getRequest().put("payAccountNumber", responseMap2.get("accountNumber"));

        String request4 = JacksonUtil.toJson(moneyLocalTransferRequest);

        ResultActions action4 = mvc.perform(post("/bank")
                .content(request4)
                .contentType(MediaType.APPLICATION_JSON));

        action4
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", is("Insufficient fund ")));
    }

    @Test
    public void whenSendMoneyLocalTransfer_WithValidRequest_ThenShouldReturnSuccess() throws Exception {
        String request1 = JacksonUtil.toJson(createAccountRequest);

        BankingResponse response1 = JacksonUtil.fromJson(mvc.perform(post("/bank")
                        .content(request1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString(), BankingResponse.class);
        Map<String, Object> responseMap1 = (Map) response1.getResponse();

        moneyLocalTransferRequest.getRequest().put("accountNumber", responseMap1.get("accountNumber"));

        String request2 = JacksonUtil.toJson(createAccountRequest);

        BankingResponse response2 = JacksonUtil.fromJson(mvc.perform(post("/bank")
                        .content(request2)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString(), BankingResponse.class);
        Map<String, Object> responseMap2 = (Map) response2.getResponse();

        moneyDepositRequest.getRequest().put("accountNumber", responseMap1.get("accountNumber"));
        String request3 = JacksonUtil.toJson(moneyDepositRequest);
        mvc.perform(post("/bank")
                .content(request3)
                .contentType(MediaType.APPLICATION_JSON));

        moneyLocalTransferRequest.getRequest().put("payAccountNumber", responseMap2.get("accountNumber"));

        String request4 = JacksonUtil.toJson(moneyLocalTransferRequest);

        ResultActions action4 = mvc.perform(post("/bank")
                .content(request4)
                .contentType(MediaType.APPLICATION_JSON));

        action4
                .andDo(print())
                .andExpect(status().isCreated());
    }
}
