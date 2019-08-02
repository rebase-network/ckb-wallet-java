package HDWallet;

import org.bitcoinj.crypto.*;
import org.bitcoinj.params.TestNet3Params;
import org.bitcoinj.wallet.DeterministicSeed;
import org.bitcoinj.wallet.UnreadableWalletException;
import org.nervos.ckb.address.AddressUtils;
import org.nervos.ckb.crypto.secp256k1.Sign;
import org.nervos.ckb.utils.Network;
import org.nervos.ckb.utils.Numeric;

import java.util.List;

public class HdWalletCKB {


    private static String personal = "ckb-default-hash";

    private static String prefixMainnet = "ckb";

    private static String prefixTestnet = "ckt";

    private static String CKB_TYPE = "M/44H/309H/0H/0/";

    private static String passwordCKB = "12345678";

    private static String wordsList = "visa immune silly edit typical first demand baby evoke cabbage false cousin kitten poem mass";

    private static final byte[] SEED = null;

    //TODO 环境选择
//    private static MainNetParams mainnetParams = MainNetParams.get();
    private static TestNet3Params mainnetParams = TestNet3Params.get();


    public static void main(String args[]) {
        try {

            CreatePrivateKeyAndAddress(10);

            //testEth();
//        } catch (CipherException e) {
//            e.printStackTrace();
        } catch (UnreadableWalletException e) {
            e.printStackTrace();
        }
    }


    public static void CreatePrivateKeyAndAddress(Integer index) throws UnreadableWalletException {

        String privateKeyReturn = null;

        DeterministicSeed deterministicSeed = new DeterministicSeed(wordsList, SEED, passwordCKB, 0L);
        System.out.println("BIP39 seed: " + deterministicSeed.toHexString());

        /**生成根私钥 root private key*/
        DeterministicKey rootPrivateKey = HDKeyDerivation.createMasterPrivateKey(deterministicSeed.getSeedBytes());

        /**根私钥进行 priB58编码*/
        String priv = rootPrivateKey.serializePrivB58(mainnetParams);
        System.out.println("BIP32 Root Key: " + priv);

        /**由根私钥生成HD钱包*/
        DeterministicHierarchy deterministicHierarchy = new DeterministicHierarchy(rootPrivateKey);
        /**定义父路径*/
        List<ChildNumber> parsePath = HDUtils.parsePath(CKB_TYPE);

        /**由父路径,派生出子私钥*/

        DeterministicKey childKey0 = deterministicHierarchy.deriveChild(parsePath, true, true, new ChildNumber(index));

        System.out.println("private Key:  " + childKey0.getPrivateKeyAsWiF(mainnetParams));
        System.out.println("private Hash Key:  " + childKey0.getPrivateKeyAsHex()); //测试用的privateKey
        System.out.println("privKey:  " + childKey0.getPrivKey());

        //TODO 生成私钥之后去CKB的Test环境去生成钱包地址；
        AddressUtils utils = new AddressUtils(Network.TESTNET);
        String privateHashKey = childKey0.getPrivateKeyAsHex();
        String publicKey = Sign.publicKeyFromPrivate(Numeric.toBigInt( privateHashKey),true).toString(16);
        System.out.println("address :  " + utils.generateFromPublicKey(publicKey)); //测试用的privateKey

    }
}

//    @Test
//    public void testPrivateKeyHashToAddressTestnet() {
//        AddressUtils utils = new AddressUtils(Network.TESTNET);
//        String publicKey =
//                Sign.publicKeyFromPrivate(
//                        Numeric.toBigInt(
//                                "e79f3207ea4980b7fed79956d5934249ceac4751a4fae01a0f7c4a96884bc4e3"),
//                        true)
//                        .toString(16);
//        Assertions.assertEquals(
//                "ckt1qyqrdsefa43s6m882pcj53m4gdnj4k440axqswmu83", utils.generateFromPublicKey(publicKey));
//    }
