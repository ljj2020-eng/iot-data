package org.fisco.bcos.asset.contract;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.fisco.bcos.sdk.abi.FunctionReturnDecoder;
import org.fisco.bcos.sdk.abi.TypeReference;
import org.fisco.bcos.sdk.abi.datatypes.Address;
import org.fisco.bcos.sdk.abi.datatypes.Event;
import org.fisco.bcos.sdk.abi.datatypes.Function;
import org.fisco.bcos.sdk.abi.datatypes.Type;
import org.fisco.bcos.sdk.abi.datatypes.Utf8String;
import org.fisco.bcos.sdk.abi.datatypes.generated.Uint256;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple1;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple2;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple3;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple5;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.contract.Contract;
import org.fisco.bcos.sdk.crypto.CryptoSuite;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.eventsub.EventCallback;
import org.fisco.bcos.sdk.model.CryptoType;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.fisco.bcos.sdk.model.callback.TransactionCallback;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

@SuppressWarnings("unchecked")
public class Record extends Contract {
    public static final String[] BINARY_ARRAY = {"608060405234801561001057600080fd5b50610bc8806100206000396000f300608060405260043610610062576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff16806303e9e609146100675780633812c6a8146101ba578063c959624814610273578063ca267f2814610322575b600080fd5b34801561007357600080fd5b506100926004803603810190808035906020019092919050505061034d565b6040518086815260200180602001806020018573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001848152602001838103835287818151815260200191508051906020019080838360005b838110156101145780820151818401526020810190506100f9565b50505050905090810190601f1680156101415780820380516001836020036101000a031916815260200191505b50838103825286818151815260200191508051906020019080838360005b8381101561017a57808201518184015260208101905061015f565b50505050905090810190601f1680156101a75780820380516001836020036101000a031916815260200191505b5097505050505050505060405180910390f35b3480156101c657600080fd5b5061027160048036038101908080359060200190929190803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091929192905050506105d9565b005b34801561027f57600080fd5b50610320600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290505050610869565b005b34801561032e57600080fd5b50610337610a28565b6040518082815260200191505060405180910390f35b600060608060008061035d610a31565b60005487111515156103d7576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260158152602001807f5265636f726420646f6573206e6f74206578697374000000000000000000000081525060200191505060405180910390fd5b6001600088815260200190815260200160002060a0604051908101604052908160008201548152602001600182018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156104995780601f1061046e57610100808354040283529160200191610499565b820191906000526020600020905b81548152906001019060200180831161047c57829003601f168201915b50505050508152602001600282018054600181600116156101000203166002900480601f01602080910402602001604051908101604052809291908181526020018280546001816001161561010002031660029004801561053b5780601f106105105761010080835404028352916020019161053b565b820191906000526020600020905b81548152906001019060200180831161051e57829003601f168201915b505050505081526020016003820160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001600482015481525050905080600001518160200151826040015183606001518460800151839350829250955095509550955095505091939590929450565b3373ffffffffffffffffffffffffffffffffffffffff166001600085815260200190815260200160002060030160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff161415156106d8576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260268152602001807f4f6e6c79207468652063726561746f722063616e20757064617465207468652081526020017f7265636f7264000000000000000000000000000000000000000000000000000081525060400191505060405180910390fd5b6000548311151515610752576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260158152602001807f5265636f726420646f6573206e6f74206578697374000000000000000000000081525060200191505060405180910390fd5b8160016000858152602001908152602001600020600101908051906020019061077c929190610a77565b50806001600085815260200190815260200160002060020190805190602001906107a7929190610a77565b503373ffffffffffffffffffffffffffffffffffffffff16837fd5aebc49baa8f449e2f500c92124f5970502d373b48e8c606acfdada1311683384426040518080602001838152602001828103825284818151815260200191508051906020019080838360005b8381101561082957808201518184015260208101905061080e565b50505050905090810190601f1680156108565780820380516001836020036101000a031916815260200191505b50935050505060405180910390a3505050565b600080815480929190600101919050555060a06040519081016040528060005481526020018381526020018281526020013373ffffffffffffffffffffffffffffffffffffffff1681526020014281525060016000805481526020019081526020016000206000820151816000015560208201518160010190805190602001906108f4929190610af7565b506040820151816002019080519060200190610911929190610af7565b5060608201518160030160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550608082015181600401559050503373ffffffffffffffffffffffffffffffffffffffff166000547fc67f76e046dda5b143e307ade4d651f1c45d4937b88a5592255c70d817768e5a84426040518080602001838152602001828103825284818151815260200191508051906020019080838360005b838110156109e95780820151818401526020810190506109ce565b50505050905090810190601f168015610a165780820380516001836020036101000a031916815260200191505b50935050505060405180910390a35050565b60008054905090565b60a060405190810160405280600081526020016060815260200160608152602001600073ffffffffffffffffffffffffffffffffffffffff168152602001600081525090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10610ab857805160ff1916838001178555610ae6565b82800160010185558215610ae6579182015b82811115610ae5578251825591602001919060010190610aca565b5b509050610af39190610b77565b5090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10610b3857805160ff1916838001178555610b66565b82800160010185558215610b66579182015b82811115610b65578251825591602001919060010190610b4a565b5b509050610b739190610b77565b5090565b610b9991905b80821115610b95576000816000905550600101610b7d565b5090565b905600a165627a7a72305820f2b5a135eebb3fe6ad413742b381a80aa46abfa68dceac5b8c9b342a2a6ee0280029"};

    public static final String BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", BINARY_ARRAY);

    public static final String[] SM_BINARY_ARRAY = {"608060405234801561001057600080fd5b50610bc8806100206000396000f300608060405260043610610062576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff168063275862bd146100675780633872102d1461012057806380b717b314610273578063ec4d68d51461029e575b600080fd5b34801561007357600080fd5b5061011e60048036038101908080359060200190929190803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919291929050505061034d565b005b34801561012c57600080fd5b5061014b600480360381019080803590602001909291905050506105dd565b6040518086815260200180602001806020018573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001848152602001838103835287818151815260200191508051906020019080838360005b838110156101cd5780820151818401526020810190506101b2565b50505050905090810190601f1680156101fa5780820380516001836020036101000a031916815260200191505b50838103825286818151815260200191508051906020019080838360005b83811015610233578082015181840152602081019050610218565b50505050905090810190601f1680156102605780820380516001836020036101000a031916815260200191505b5097505050505050505060405180910390f35b34801561027f57600080fd5b50610288610869565b6040518082815260200191505060405180910390f35b3480156102aa57600080fd5b5061034b600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290505050610872565b005b3373ffffffffffffffffffffffffffffffffffffffff166001600085815260200190815260200160002060030160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1614151561044c576040517fc703cb120000000000000000000000000000000000000000000000000000000081526004018080602001828103825260268152602001807f4f6e6c79207468652063726561746f722063616e20757064617465207468652081526020017f7265636f7264000000000000000000000000000000000000000000000000000081525060400191505060405180910390fd5b60005483111515156104c6576040517fc703cb120000000000000000000000000000000000000000000000000000000081526004018080602001828103825260158152602001807f5265636f726420646f6573206e6f74206578697374000000000000000000000081525060200191505060405180910390fd5b816001600085815260200190815260200160002060010190805190602001906104f0929190610a31565b508060016000858152602001908152602001600020600201908051906020019061051b929190610a31565b503373ffffffffffffffffffffffffffffffffffffffff16837ff5cec50104c4a4ec816dda48cd45808807b03fd8ef7d94e166db724f097aa6ce84426040518080602001838152602001828103825284818151815260200191508051906020019080838360005b8381101561059d578082015181840152602081019050610582565b50505050905090810190601f1680156105ca5780820380516001836020036101000a031916815260200191505b50935050505060405180910390a3505050565b60006060806000806105ed610ab1565b6000548711151515610667576040517fc703cb120000000000000000000000000000000000000000000000000000000081526004018080602001828103825260158152602001807f5265636f726420646f6573206e6f74206578697374000000000000000000000081525060200191505060405180910390fd5b6001600088815260200190815260200160002060a0604051908101604052908160008201548152602001600182018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156107295780601f106106fe57610100808354040283529160200191610729565b820191906000526020600020905b81548152906001019060200180831161070c57829003601f168201915b50505050508152602001600282018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156107cb5780601f106107a0576101008083540402835291602001916107cb565b820191906000526020600020905b8154815290600101906020018083116107ae57829003601f168201915b505050505081526020016003820160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001600482015481525050905080600001518160200151826040015183606001518460800151839350829250955095509550955095505091939590929450565b60008054905090565b600080815480929190600101919050555060a06040519081016040528060005481526020018381526020018281526020013373ffffffffffffffffffffffffffffffffffffffff1681526020014281525060016000805481526020019081526020016000206000820151816000015560208201518160010190805190602001906108fd929190610af7565b50604082015181600201908051906020019061091a929190610af7565b5060608201518160030160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550608082015181600401559050503373ffffffffffffffffffffffffffffffffffffffff166000547fec697bd34ad2671cc8c6d19652aaace20982b9c2c481935eabf5e7569f127cc284426040518080602001838152602001828103825284818151815260200191508051906020019080838360005b838110156109f25780820151818401526020810190506109d7565b50505050905090810190601f168015610a1f5780820380516001836020036101000a031916815260200191505b50935050505060405180910390a35050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10610a7257805160ff1916838001178555610aa0565b82800160010185558215610aa0579182015b82811115610a9f578251825591602001919060010190610a84565b5b509050610aad9190610b77565b5090565b60a060405190810160405280600081526020016060815260200160608152602001600073ffffffffffffffffffffffffffffffffffffffff168152602001600081525090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10610b3857805160ff1916838001178555610b66565b82800160010185558215610b66579182015b82811115610b65578251825591602001919060010190610b4a565b5b509050610b739190610b77565b5090565b610b9991905b80821115610b95576000816000905550600101610b7d565b5090565b905600a165627a7a72305820b21b2c79870437e334227fda3e447ac2b3a818c6eaaaa0db7a29e6a84027d4b00029"};

    public static final String SM_BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", SM_BINARY_ARRAY);

    public static final String[] ABI_ARRAY = {"[{\"constant\":false,\"inputs\":[{\"name\":\"_id\",\"type\":\"uint256\"}],\"name\":\"getRecord\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"},{\"name\":\"\",\"type\":\"string\"},{\"name\":\"\",\"type\":\"string\"},{\"name\":\"\",\"type\":\"address\"},{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"_id\",\"type\":\"uint256\"},{\"name\":\"_title\",\"type\":\"string\"},{\"name\":\"_content\",\"type\":\"string\"}],\"name\":\"updateRecord\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"_title\",\"type\":\"string\"},{\"name\":\"_content\",\"type\":\"string\"}],\"name\":\"addRecord\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[],\"name\":\"getRecordCount\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"name\":\"id\",\"type\":\"uint256\"},{\"indexed\":false,\"name\":\"title\",\"type\":\"string\"},{\"indexed\":true,\"name\":\"creator\",\"type\":\"address\"},{\"indexed\":false,\"name\":\"timestamp\",\"type\":\"uint256\"}],\"name\":\"RecordAdded\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"name\":\"id\",\"type\":\"uint256\"},{\"indexed\":false,\"name\":\"title\",\"type\":\"string\"},{\"indexed\":true,\"name\":\"updater\",\"type\":\"address\"},{\"indexed\":false,\"name\":\"timestamp\",\"type\":\"uint256\"}],\"name\":\"RecordUpdated\",\"type\":\"event\"}]"};

    public static final String ABI = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", ABI_ARRAY);

    public static final String FUNC_GETRECORD = "getRecord";

    public static final String FUNC_UPDATERECORD = "updateRecord";

    public static final String FUNC_ADDRECORD = "addRecord";

    public static final String FUNC_GETRECORDCOUNT = "getRecordCount";

    public static final Event RECORDADDED_EVENT = new Event("RecordAdded", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}, new TypeReference<Utf8String>() {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event RECORDUPDATED_EVENT = new Event("RecordUpdated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}, new TypeReference<Utf8String>() {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    protected Record(String contractAddress, Client client, CryptoKeyPair credential) {
        super(getBinary(client.getCryptoSuite()), contractAddress, client, credential);
    }

    public static String getBinary(CryptoSuite cryptoSuite) {
        return (cryptoSuite.getCryptoTypeConfig() == CryptoType.ECDSA_TYPE ? BINARY : SM_BINARY);
    }

    public TransactionReceipt getRecord(BigInteger _id) {
        final Function function = new Function(
                FUNC_GETRECORD, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(_id)), 
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public byte[] getRecord(BigInteger _id, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_GETRECORD, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(_id)), 
                Collections.<TypeReference<?>>emptyList());
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForGetRecord(BigInteger _id) {
        final Function function = new Function(
                FUNC_GETRECORD, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(_id)), 
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple1<BigInteger> getGetRecordInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_GETRECORD, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<BigInteger>(

                (BigInteger) results.get(0).getValue()
                );
    }

    public Tuple5<BigInteger, String, String, String, BigInteger> getGetRecordOutput(
            TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function = new Function(FUNC_GETRECORD, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple5<BigInteger, String, String, String, BigInteger>(

                (BigInteger) results.get(0).getValue(), 
                (String) results.get(1).getValue(), 
                (String) results.get(2).getValue(), 
                (String) results.get(3).getValue(), 
                (BigInteger) results.get(4).getValue()
                );
    }

    public TransactionReceipt updateRecord(BigInteger _id, String _title, String _content) {
        final Function function = new Function(
                FUNC_UPDATERECORD, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(_id), 
                new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_title), 
                new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_content)), 
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public byte[] updateRecord(BigInteger _id, String _title, String _content,
            TransactionCallback callback) {
        final Function function = new Function(
                FUNC_UPDATERECORD, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(_id), 
                new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_title), 
                new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_content)), 
                Collections.<TypeReference<?>>emptyList());
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForUpdateRecord(BigInteger _id, String _title,
            String _content) {
        final Function function = new Function(
                FUNC_UPDATERECORD, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(_id), 
                new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_title), 
                new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_content)), 
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple3<BigInteger, String, String> getUpdateRecordInput(
            TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_UPDATERECORD, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple3<BigInteger, String, String>(

                (BigInteger) results.get(0).getValue(), 
                (String) results.get(1).getValue(), 
                (String) results.get(2).getValue()
                );
    }

    public TransactionReceipt addRecord(String _title, String _content) {
        final Function function = new Function(
                FUNC_ADDRECORD, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_title), 
                new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_content)), 
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public byte[] addRecord(String _title, String _content, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_ADDRECORD, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_title), 
                new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_content)), 
                Collections.<TypeReference<?>>emptyList());
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForAddRecord(String _title, String _content) {
        final Function function = new Function(
                FUNC_ADDRECORD, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_title), 
                new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_content)), 
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple2<String, String> getAddRecordInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_ADDRECORD, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple2<String, String>(

                (String) results.get(0).getValue(), 
                (String) results.get(1).getValue()
                );
    }

    public TransactionReceipt getRecordCount() {
        final Function function = new Function(
                FUNC_GETRECORDCOUNT, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public byte[] getRecordCount(TransactionCallback callback) {
        final Function function = new Function(
                FUNC_GETRECORDCOUNT, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForGetRecordCount() {
        final Function function = new Function(
                FUNC_GETRECORDCOUNT, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple1<BigInteger> getGetRecordCountOutput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function = new Function(FUNC_GETRECORDCOUNT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<BigInteger>(

                (BigInteger) results.get(0).getValue()
                );
    }

    public List<RecordAddedEventResponse> getRecordAddedEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(RECORDADDED_EVENT, transactionReceipt);
        ArrayList<RecordAddedEventResponse> responses = new ArrayList<RecordAddedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            RecordAddedEventResponse typedResponse = new RecordAddedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.id = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.creator = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.title = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.timestamp = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public void subscribeRecordAddedEvent(String fromBlock, String toBlock,
            List<String> otherTopics, EventCallback callback) {
        String topic0 = eventEncoder.encode(RECORDADDED_EVENT);
        subscribeEvent(ABI,BINARY,topic0,fromBlock,toBlock,otherTopics,callback);
    }

    public void subscribeRecordAddedEvent(EventCallback callback) {
        String topic0 = eventEncoder.encode(RECORDADDED_EVENT);
        subscribeEvent(ABI,BINARY,topic0,callback);
    }

    public List<RecordUpdatedEventResponse> getRecordUpdatedEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(RECORDUPDATED_EVENT, transactionReceipt);
        ArrayList<RecordUpdatedEventResponse> responses = new ArrayList<RecordUpdatedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            RecordUpdatedEventResponse typedResponse = new RecordUpdatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.id = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.updater = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.title = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.timestamp = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public void subscribeRecordUpdatedEvent(String fromBlock, String toBlock,
            List<String> otherTopics, EventCallback callback) {
        String topic0 = eventEncoder.encode(RECORDUPDATED_EVENT);
        subscribeEvent(ABI,BINARY,topic0,fromBlock,toBlock,otherTopics,callback);
    }

    public void subscribeRecordUpdatedEvent(EventCallback callback) {
        String topic0 = eventEncoder.encode(RECORDUPDATED_EVENT);
        subscribeEvent(ABI,BINARY,topic0,callback);
    }

    public static Record load(String contractAddress, Client client, CryptoKeyPair credential) {
        return new Record(contractAddress, client, credential);
    }

    public static Record deploy(Client client, CryptoKeyPair credential) throws ContractException {
        return deploy(Record.class, client, credential, getBinary(client.getCryptoSuite()), "");
    }

    public static class RecordAddedEventResponse {
        public TransactionReceipt.Logs log;

        public BigInteger id;

        public String creator;

        public String title;

        public BigInteger timestamp;
    }

    public static class RecordUpdatedEventResponse {
        public TransactionReceipt.Logs log;

        public BigInteger id;

        public String updater;

        public String title;

        public BigInteger timestamp;
    }
}
