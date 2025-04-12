package com.southwind;

import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.fisco.bcos.sdk.transaction.manager.AssembleTransactionProcessor;
import org.fisco.bcos.sdk.transaction.manager.TransactionProcessorFactory;
import org.fisco.bcos.sdk.transaction.model.dto.TransactionResponse;
import org.junit.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;


public class RecordTest
{
    public final String configFile = "src/main/resources/config-example.toml";

//    public final String configFile = "src/main/resources/config-example.toml";
    @Test
    public void test() throws Exception {
//初始化BcosSDK对象
        BcosSDK sdk=BcosSDK.build(configFile);

        // 获取Client对象，此处传入的群组ID为1
        Client client = sdk.getClient(Integer.valueOf(1));
        // 构造AssembleTransactionProcessor对象，需要传入client对象，CryptoKeyPair对象和abi、binary文件存放的路径。abi和binary文件需要在上一步复制到定义的文件夹中。
        CryptoKeyPair keyPair = client.getCryptoSuite().createKeyPair();
        AssembleTransactionProcessor transactionProcessor = TransactionProcessorFactory.createAssembleTransactionProcessor(client, keyPair, "src/main/resources/abi/", "");


            // 添加记录
            String title = "Test Title";
            String content = "Test Content";

            // 创建调用交易函数的参数，此处为传入一个参数
            List<Object> params = new ArrayList<>();
            params.add(title);
            params.add(content);
            // 调用HelloWorld合约，合约地址为helloWorldAddress， 调用函数名为『set』，函数参数类型为params
            TransactionResponse transactionResponse = transactionProcessor.sendTransactionAndGetResponseByContractLoader("Record", "0xeae675added5238ecb54de0370d566930715d9b5", "addRecord", params);
    }
}





