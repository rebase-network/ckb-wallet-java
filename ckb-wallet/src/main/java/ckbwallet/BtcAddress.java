package ckbwallet;

import org.bitcoinj.core.Address;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.params.RegTestParams;
import org.bitcoinj.script.Script;
import org.bitcoinj.wallet.Wallet;

public class BtcAddress {

// http://blog.hubwiz.com/2018/10/03/java-bitcoin-address/
// https://my.oschina.net/u/2472105/blog/2251005
// https://www.novixys.com/blog/generate-bitcoin-addresses-java/

    public static void main(String[] args){
        NetworkParameters params = MainNetParams.get();
        NetworkParameters regTestParams = RegTestParams.get();

        ECKey ecKey = new ECKey();
        System.out.format("私钥 => 0x%s\n", ecKey.getPrivateKeyAsHex());
        System.out.format("压缩公钥 => 0x%s\n", ecKey.getPublicKeyAsHex());

        Address mainbtcAddr = Address.fromKey(params, ecKey, Script.ScriptType.P2PKH);
        System.out.println("Btc主网P2PKH地址 => " + mainbtcAddr.toString());

        Address P2WPKHmainbtcAddr = Address.fromKey(params, ecKey, Script.ScriptType.P2WPKH);
        System.out.println("Btc主网P2WPKH地址 => " + P2WPKHmainbtcAddr.toString());

        System.out.println("");

        Address regbtcAddr = Address.fromKey(regTestParams, ecKey, Script.ScriptType.P2PKH);
        System.out.println("Btc测试网P2PKH地址 => " + regbtcAddr.toString());

    }
}
