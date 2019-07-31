package ckbwallet;


import java.math.BigInteger;
import java.security.KeyPairGenerator;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECFieldFp;
import java.security.spec.ECGenParameterSpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.util.Arrays;

import org.bitcoinj.core.ECKey;
import blake.Blake2b;

// https://nervosbj.github.io/teachbitcoin/cn/#/

public class App
{

    static String personal = "ckb-default-hash";

    public static void main( String[] args)
    {

        try {
            // https://stackoverflow.com/a/48841220
            KeyPairGenerator kg = KeyPairGenerator.getInstance("EC");
            kg.initialize(new ECGenParameterSpec("secp256k1"));
            ECParameterSpec p = ((ECPublicKey) kg.generateKeyPair().getPublic()).getParams();

            BigInteger pCurve = ((ECFieldFp) p.getCurve().getField()).getP(); //素数域
            System.out.println ("pCurve: " + pCurve);

            ECPoint G = p.getGenerator(); // 生成点G
            BigInteger xPoint = G.getAffineX(); // x坐标
            BigInteger yPoint = G.getAffineY(); // y坐标
            System.out.format("Gx: 0x%032x%n", xPoint);
            System.out.format("Gy: 0x%032x%n", yPoint);

            ECKey ecKey = new ECKey();

            String privKey = ecKey.getPrivateKeyAsHex(); //私钥
            String pubKey = ecKey.getPublicKeyAsHex(); //压缩公钥
            byte[] pubKeyByte = ecKey.getPubKey();

            String uncompressedPubKey = ecKey.decompress().getPublicKeyAsHex(); //未压缩公钥

            byte[] blake2b = genBlake2b(pubKeyByte);
            byte[] blake160 = Arrays.copyOfRange(blake2b, 0, 20);

            System.out.println("Privkey: 0x"+ privKey+ "\nPubKey: 0x" + pubKey);
            System.out.println("uncompressedPubKey: " + uncompressedPubKey);
            System.out.println("Blake160: 0x" + bytesToHexStr(blake160) );

        } catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException ex) {
            System.err.println("err:" + ex);
            System.exit(1001);
        }

    }

    public static byte[] genBlake2b(byte[] val){
        Blake2b.Param param = new blake.Blake2b.Param()
                .setDigestLength(32)
                .setPersonal(personal.getBytes());

        Blake2b blake2b = Blake2b.Digest.newInstance(param);
        blake2b.update(val);

        return blake2b.digest();
    }

    public static String bytesToHexStr(byte[] b) {
        int len = b.length;

        String data = new String();

        for (int i = 0; i < len; i++){
            data += Integer.toHexString((b[i] >> 4) & 0xf);
            data += Integer.toHexString(b[i] & 0xf);
        }
        return data;
    }

}
