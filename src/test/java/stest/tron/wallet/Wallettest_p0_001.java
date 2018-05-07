package stest.tron.wallet;

import java.util.Optional;

import org.tron.api.GrpcAPI;
import org.tron.api.GrpcAPI.AccountList;
import org.tron.api.GrpcAPI.NodeList;
import org.tron.common.utils.ByteArray;
import stest.tron.wallet.common.WalletClient;
import stest.tron.wallet.common.Base58;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.*;
import org.testng.Assert;


@Slf4j
public class Wallettest_p0_001 {

    private WalletClient walletClient;
    private final static  String SunKey        = "85a449304487085205d48a402c30877e888fcb34391d65cfdc9cad420127826f";
    private final static  String ZionKey       = "32012d7b024b2e62e0ca145f137bcfd2468cac99a1880b275e2e499b23af265c";

    private static final byte[] FROM_ADDRESS = Base58.decodeFromBase58Check("27Vm12vh5Mm9HzPSWBDvbZu1U25xvyFvexF");
    private static final byte[] TO_ADDRESS   = Base58.decodeFromBase58Check("27WuXYGzxHXU7ynKDzoudJd9mS9Bw4vmbER");

    private static final Long AMOUNT = 1000000L;

    public static void main(String[] args){
        logger.info("test man.");
    }

    @BeforeClass
    public void beforeClass() {
        walletClient = new WalletClient(SunKey);
        walletClient.init(0);
        logger.info("this is before class");
    }


    @Test(enabled = false)
    public void checkTrade() {

        logger.info(ByteArray.toStr(walletClient.getAccount(FROM_ADDRESS).getAccountName().toByteArray()));
        logger.info(Long.toString(walletClient.getAccount(FROM_ADDRESS).getBalance()));
        logger.info(ByteArray.toStr(walletClient.getAccount(TO_ADDRESS).getAccountName().toByteArray()));
        logger.info(Long.toString(walletClient.getAccount(TO_ADDRESS).getBalance()));

        boolean ret  = walletClient.sendCoin(TO_ADDRESS,AMOUNT);

        logger.info(ByteArray.toStr(walletClient.getAccount(FROM_ADDRESS).getAccountName().toByteArray()));
        logger.info(Long.toString(walletClient.getAccount(FROM_ADDRESS).getBalance()));
        logger.info(ByteArray.toStr(walletClient.getAccount(TO_ADDRESS).getAccountName().toByteArray()));
        logger.info(Long.toString(walletClient.getAccount(TO_ADDRESS).getBalance()));

        //Assert.assertTrue(ret);

        logger.info("this is TestNG test case");
    }


    @Test
    public void checkAcc() {

        Optional<AccountList> result = walletClient.listAccounts();

        if (result.isPresent()) {
            AccountList accountList = result.get();
            byte[]  fromaddr;
            byte[]  toaddr;
            accountList.getAccountsList().forEach(account -> {
                String sendname = "Sun";
                String recvname = "Zion";
                logger.info(ByteArray.toStr(walletClient.getAccount(account.getAddress().toByteArray()).getAccountName().toByteArray()));
                logger.info(Long.toString(walletClient.getAccount(account.getAddress().toByteArray()).getBalance()));
                logger.info(ByteArray.toHexString(account.getAddress().toByteArray()));
                //logger.info(ByteArray.toStr(account.getAccountName().toByteArray()));
                //logger.info(Long.toString(account.getAllowance()));
                //logger.info(ByteArray.toHexString(account.getCode().toByteArray()));
                //logger.info(Integer.toString(account.getAssetCount()));
                //logger.info(account.getType().name());
                //logger.info(Long.toString(account.getBalance()));

            });
        }
    }

    @Test(enabled = false)
    public void checkNode() {

        Optional<GrpcAPI.NodeList> result = walletClient.listNodes();

        if (result.isPresent()) {
            NodeList nodeList = result.get();
            nodeList.getNodesList().forEach(node -> {
                logger.info(Integer.toString(node.hashCode()));
                logger.info(Integer.toString(node.getSerializedSize()));
                logger.info(Integer.toString(node.getAddress().getSerializedSize()));
                logger.info(Integer.toString(node.getAddress().getPort()));
                logger.info(ByteArray.toStr(node.getAddress().getHost().toByteArray()));
            });
        }
    }


    @AfterClass
    public void afterClass() {
        logger.info("this is after class");
    }

}