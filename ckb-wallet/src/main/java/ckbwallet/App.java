package ckbwallet;

import blake.Blake2b;
import org.bitcoinj.core.ECKey;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
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

    static String personal = "ckb-default-hash";

    public static void main( String[] args )
    {

        System.out.println("OS Order: " + ByteOrder.nativeOrder());
        ByteBuffer buf = ByteBuffer.allocate(6);
        System.out.println("ByteBuffer Order: " + buf.order());

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

            byte[] pubKeyByte =  ecKey.getPubKey();

            String uncompressedPubKey = ecKey.decompress().getPublicKeyAsHex(); //未压缩公钥
            System.out.println("privKey: 0x"+ privKey+ "\npubKey: 0x" + pubKey);
            System.out.println("uncompressedPubKey: 0x"+ uncompressedPubKey);

            genBlake160(pubKeyByte);
            System.out.println("----------");
            genBlake160(pubKey.getBytes());
        } catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException ex) {
            System.err.println("err:" + ex);
            System.exit(1001);
        }

    }


    public static void genBlake160(byte[] val){
        Blake2b.Param param = new Blake2b.Param()
                .setDigestLength(32)
                .setPersonal(personal.getBytes());

        Blake2b blake2b = Blake2b.Digest.newInstance(param);
        blake2b.update(val);
        byte[] blake = blake2b.digest(val);
        System.out.println("blake: " + bytesToHexStr(blake));
        System.out.println("blake160: " + bytes160ToHexStr(blake));
    }


    public static String bytes160ToHexStr(byte[] b) {
        int cut;
        int len = b.length;

        if (len < 20){
            cut = len;
        } else {
            cut = 20;
        }

        String data = new String();

        for (int i = 0; i < cut; i++){
            data += Integer.toHexString((b[i] >> 4) & 0xf);
            data += Integer.toHexString(b[i] & 0xf);
        }
        return data;
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


