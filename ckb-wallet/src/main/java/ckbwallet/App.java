package ckbwallet;

import org.bitcoinj.core.ECKey;

import java.math.BigInteger;
import java.security.KeyPairGenerator;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECFieldFp;
import java.security.spec.ECGenParameterSpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;

// https://nervosbeijingcommunity.github.io/teachbitcoin/cn/#/

public class App
{
    public static void main( String[] args )
    {

        try {
            // https://stackoverflow.com/a/48841220
            KeyPairGenerator kg = KeyPairGenerator.getInstance("EC");
            kg.initialize(new ECGenParameterSpec("secp256k1"));
            ECParameterSpec p = ((ECPublicKey) kg.generateKeyPair().getPublic()).getParams();

            BigInteger pCurve = ((ECFieldFp) p.getCurve().getField()).getP(); //素数域

            System.out.println ("pCurve: " +  pCurve);

            ECPoint G = p.getGenerator(); // 生成点G
            BigInteger xPoint = G.getAffineX(); // x坐标
            BigInteger yPoint = G.getAffineY(); // y坐标
            System.out.format ("Gx: 0x%032x%n", xPoint);
            System.out.format ("Gy: 0x%032x%n", yPoint);

            ECKey ecKey = new ECKey();

            String privKey = ecKey.getPrivateKeyAsHex(); //私钥
            String pubKey = ecKey.getPublicKeyAsHex(); //压缩公钥
            String uncompressedPubKey = ecKey.decompress().getPublicKeyAsHex(); //未压缩公钥

            System.out.println("privKey: 0x"+ privKey+ "\npubKey: 0x" + pubKey);
            System.out.println("uncompressedPubKey: 0x"+ uncompressedPubKey);

        } catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException ex) {
            System.out.println("err:" + ex);
            System.exit(1001);
        }

    }
}


