import java.security.*;

import javax.crypto.*;

/**
 * DES加密解密工具
 * @description DES加密解密工具
 * @date 2016－1-17
 * @version 1.0
 */
public class DESSecurityUtil {
    //private static String strDefaultKey = "national";
    private static String strDefaultKey = "national";
    private Cipher encryptCipher = null;
    private Cipher decryptCipher = null;

    /**
     * 将byte数组转换为表示16进制值的字符串， 如：byte[]{8,18}转换为：0813， 和public static byte[]
     * hexStr2ByteArr(String strIn) 互为可逆的转换过程
     *
     * @param arrB 需要转换的byte数组
     * @return 转换后的字符串
     * @throws Exception 本方法不处理任何异常，所有异常全部抛出
     */
    public static String byteArr2HexStr(byte[] arrB) throws Exception {
        int iLen = arrB.length;
// 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
        StringBuffer sb = new StringBuffer(iLen * 2);
        for (int i = 0; i < iLen; i++) {
            int intTmp = arrB[i];
// 把负数转换为正数
            while (intTmp < 0) {
                intTmp = intTmp + 256;
            }
// 小于0F的数需要在前面补0
            if (intTmp < 16) {
                sb.append("0");
            }
            sb.append(Integer.toString(intTmp, 16));
        }
        return sb.toString();
    }

    /**
     * 将表示16进制值的字符串转换为byte数组， 和public static String byteArr2HexStr(byte[] arrB)
     * 互为可逆的转换过程
     *
     * @param strIn 需要转换的字符串
     * @return 转换后的byte数组
     * @throws Exception 本方法不处理任何异常，所有异常全部抛出
     * @author LiGuoQing
     */
    public static byte[] hexStr2ByteArr(String strIn) throws Exception {
        byte[] arrB = strIn.getBytes();
        int iLen = arrB.length;

        // 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
        byte[] arrOut = new byte[iLen / 2];
        for (int i = 0; i < iLen; i = i + 2) {
            String strTmp = new String(arrB, i, 2);
            arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
        }
        return arrOut;
    }

    /**
     * 默认构造方法，使用默认密钥
     *
     * @throws Exception
     */
    public DESSecurityUtil() throws Exception {
        this(strDefaultKey);
    }

    /**
     * 指定密钥构造方法
     *
     * @param strKey 指定的密钥
     * @throws Exception
     */
    @SuppressWarnings("restriction")
    public DESSecurityUtil(String strKey) throws Exception {
        Security.addProvider(new com.sun.crypto.provider.SunJCE());
        Key key = getKey(strKey.getBytes());

        encryptCipher = Cipher.getInstance("DES");
        encryptCipher.init(Cipher.ENCRYPT_MODE, key);

        decryptCipher = Cipher.getInstance("DES");
        decryptCipher.init(Cipher.DECRYPT_MODE, key);
    }

    /**
     * 加密字节数组
     *
     * @param arrB 需加密的字节数组
     * @return 加密后的字节数组
     * @throws Exception
     */
    public byte[] encrypt(byte[] arrB) throws Exception {
        return encryptCipher.doFinal(arrB);
    }

    /**
     * 加密字符串
     *
     * @param strIn 需加密的字符串
     * @return 加密后的字符串
     * @throws Exception
     */
    public String encrypt(String strIn) throws Exception {
        return byteArr2HexStr(encrypt(strIn.getBytes("UTF-8")));
    }

    /**
     * 解密字节数组
     *
     * @param arrB 需解密的字节数组
     * @return 解密后的字节数组
     * @throws Exception
     */
    public byte[] decrypt(byte[] arrB) throws Exception {
        return decryptCipher.doFinal(arrB);
    }

    /**
     * 解密字符串
     *
     * @param strIn 需解密的字符串
     * @return 解密后的字符串
     * @throws Exception
     */
    public String decrypt(String strIn) throws Exception {
        return new String(decrypt(hexStr2ByteArr(strIn)));
    }

    /**
     * 从指定字符串生成密钥，密钥所需的字节数组长度为8位 不足8位时后面补0，超出8位只取前8位
     *
     * @param arrBTmp 构成该字符串的字节数组
     * @return 生成的密钥
     * @throws java.lang.Exception
     */
    private Key getKey(byte[] arrBTmp) throws Exception {
        // 创建一个空的8位字节数组（默认值为0）
        byte[] arrB = new byte[8];

        // 将原始字节数组转换为8位
        for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
            arrB[i] = arrBTmp[i];
        }

        // 生成密钥
        Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");

        return key;
    }

    public static void main(String[] args) throws Exception {
        DESSecurityUtil des = new DESSecurityUtil();
        System.out.println("++++++++++++"+des.decrypt("D9F4CEFA0E2EC179F82E800E92589648"));
        System.out.println("++++++++++++"+des.decrypt("e91316a218673db2a2f953aa9399cfd411e9ef6caa681b6026f917be6ba65413e8a86c4d31ff2baa5895742781276f0ea6629cb97aa216eea92ac5c27802334e34f39ce70709036c331162f560ee8508e86c9f7d79c5c6758a19a99b02af926575c16abb539439828caf110324edce4f640a3da69e7ebeec887a280a0951db01501bd45e35f234af9e40b7d9cbc34c0411a602112dca8696b3b220c7d091830e2b25eb0c0bfdbddca07e3ab02c61d90b26e2cecd1c932f935ddf6f2a0364c243757e78e671b6d67113c88aa9c1d4cd732763f838ed9036bc3e53563191bdd68b199a14b4263c44a2bc0d1a0a93393101bc12a9add8f52a3619657eb014f5fabc02b17a2abeb2b9f1424315270dcd8261ea6cb7d6df40e86834a5eef8cc95e719ab8ee323d1daa59460f3a515101e74f257efe248fe1c7804ebc121da3e4714dab6b47a35ffff1abf277c791602c25497d33b2f164c0f32d5d8c57ce2ec1a4df9ee094210500b67450ef5a24418bcf70330328cb1593e681d1d16687874f1e70c6242bfbcf24651c8b9b3814cce9b68e46284c3c083a2e51961eabffecb5ee769a4b0c7771fb7d891b38a831ae8f9652c5219cc7d281a5c7f500f9932d9808d6e44f1656a2f97b88d867c1fc79818cbaa73a3e80765dd13c0cd635ae286c34368d3f8ce9cfdffe05c00a97f3292ca465ce44cc2cf2cc5f2e20cc3fbf8163cede3d5667d2af1b84fb1418fe8315543cdd395790ba7fcfaefed5237bacaf569bf48eff33111edbdea2be925d1b30bdaaf2d64cd6b7adf9d98f8ba06ebf263b626ff4ab583362584fb07aa5059bd9b4dacedb4e18121a8784cdfb09d55c3d1c42677c36f478e2258420c4e7f38c8ce4598b47b79d9f2cde9c044332602fb9205ad013179893bc563382ffb5f32a57d5861523b07e1f7a80c3a75ad746016fed07dbe8f08134ed000fa3da82de6cb5cb4013c4efabe8dc3a185448d507dabb8ea487cdecede18e8ad44633fe27a7fe2fb4f868d1dc199841aa39d186b47a93746eb7ffe904e4b59dbd9077c52b20fdc47e24375c8452b5820fb3693dea269178bace7bbc5df1df0851cf98c2c48df3bd2d0c13f950488670dda41224ba72d4e4cdf1ce41e2a204841e5cdc9876e9a35b3589ed95bf7dccd67d3ca5238b5dd28eb138f91b6a3a6f4a20c325758560457d6e9e5df0f9a7505aa1d0e747d4c0d74afadc8a40b1381e7317221f8dded2622954762655aa28b2c0d80f4e5423728639035ecc9f26e4fc2e461913658ee28efb586139a79006d89ed1d61c6075ea161ecdfd85a864df117708cf437fbf9ba404eb380fee2c87604ddb15b37fbf9ba404eb3804f606d2fb98cc03b7c411b9859a8fcc39111dcabef60008f7f6ea8e5928b270c53e5efc8d30b0b3281b2286a84366dc2273b804f37208a8ed7543c60753c536544883570689ac45f4bc9ea1358e19ea354aa1947f1a0801b3d9920b81990ce412d90c98bb7e9eef046669bd0e0316d69dc1a2e5c52f59f4b0de61f4d931aaee595ede427ffcce8068df5f80e701cae36daba0104a78af62f37f0c18dd089a513a3f61e26ad1889a49c202e5fc8da3ec6b2f7b634f384cbe28db7046dd6fdbdfe390308a75a29aa87a9c6d76d2b93404d4c0256274c71de32cf09255b9700413a00c404128f7f895b79698a0db52c2794dedf5580856159ec4a9ae3083c18d5802f3fb025917dabd81b5d0ddf39b6600c8bb562c4f4e37edd45281ed3efd439af0a0042fbcfb55759eb0f839cd847842b41b730f49f08762b7b3d1423b9bcc7b499f5f9a0f71667b09a07c6f46657e915a21d50a7543975896749058b778fd3d33790291d80ea6d03999c1c4eb1c9139815014432e912397969381e01da626597a7cf872fb2cb2e02225c4999349fc0d355e94359bacc664ff480192621577579b594dcf9aea6f0c3c7f86c0efc31f288ba3df7ee8d1a925459ae22e40ec6c22e1089dac597a12e83dd28fe5682279befa2a8864f60f05762e51264fca435fe214bddd7cd36c521f79059b0982243f53667344755d251f58310640410123062965cc714808ceb350d4f00b63ec23b9f003f5e443ac2f9e6ae65696bff07d7323115014432e9123979f0316bbe863a0a52a54f8811e36444585859b4a916bc57b12ffef6424d2c2c26ea1b83acc82344f13a4191d644f1b0ef3782e8d9fcf2d01dc8e613d0117d88657a9d7a1cd6ebd70c9db3a84147a8784bdf29cdb40e40f596de20f800cb7da94d43f3a18dd187daf3f92a0429ad49eb538952ba5e650f0ccb8092be6328892ea45a00a22ee27b7ed148ea98b3b8be73c9d73a9fec3dce74f0e09e92ecfcc19d43de6f6a887eaf9d2491c79761c2670b286d568841c59474a3b514e577cc6de39e3f1925c812d5acc3347ebd16398ddba0926541d6f54270f7cf09255b9700413a6fb9b1537e93b06a7d8b181b77da61a39b5ed313cec1239bf58ee149a9418521bbc681c34088a1d5756a0a77bd788f102145f8042ec2bf6fc5d94944989dbf9e1a2cb0cd0bcb4a88246abf5e2e3314fd711ef420812c93c01a8cdf806019a427c8d7a263daae0a55cad86f72f5bd23dfcb07c248f06ce7e9456d83a14976111dc4addc235f23507ab74a33f313cb08b0946ef8df82950089fc4bc7619efacbc883f16e21387d08803252e69aaa523f67"));
        //System.out.println("+++++-------++"+des.decrypt("e91316a218673db2a2f953aa9399cfd411e9ef6caa681b6026f917be6ba65413e8a86c4d31ff2baa5895742781276f0ea6629cb97aa216eea92ac5c27802334e34f39ce70709036c331162f560ee8508e86c9f7d79c5c6758a19a99b02af926575c16abb539439828caf110324edce4f640a3da69e7ebeec169b8eb8cc203f4086574aeb2c6a92859e40b7d9cbc34c0411a602112dca8696b3b220c7d091830e2b25eb0c0bfdbddca07e3ab02c61d90b26e2cecd1c932f935ddf6f2a0364c243757e78e671b6d67113c88aa9c1d4cd732763f838ed9036bc3e53563191bdd68b199a14b4263c44a2b003df88c78bb5fe49e54ea33937f92919657eb014f5fabc02b17a2abeb2b9f1424315270dcd8261ea6cb7d6df40e86834a5eef8cc95e719ab8ee323d1daa59460f3a515101e74f2e7bd150d4f49862eebc121da3e4714dab6b47a35ffff1abf3fdcc318ef4d4a2f8c4b0ffe35d0f1ebac9af32a5da030d3164920b47a4c13861345f5fec32a28859f15d68271b2f33fcd0b47d0fbf05b95526734c23405299eb4d55dbab2dc734294dc8244688e1c5225e465d67cd57b13b8a9b95f63aa0db2ed07f944680a2f2a5b39b8e824f73269d624b82a7680ac9f2ac3a0392d3bf3b2b94d2b56dc1c4b7a418fe8315543cdd31bf4cd9c5f7109e14315259fe4f09a8dd60d07796779ab9b02500213aa5ef7b88d93909082daba79c3ceb8218fba0465805d8dfc976be5f356d7d0e01b8cd0075e8a629174d1e0cdc658aa8a81e654c13f8284941652abb167a8d45e451efdb82670ef03adc3d6386036239b873f3e2ee6910c9e00d42a07e7bcade3f2422b8dbf54d2299049bc366b5bd2fc1904094f5b38c735642433a3982e1d7d21c539649837d4d6fef92908283adcbc00babd087b79d9f2cde9c0448f08134ed000fa3d28dfbede5d46c38aa147f2650d9bbc165d1ddfa4cc6d084526a3fa20af26f14f0dc84dc3a61e25f7cf2410a16578c2324dd87abbf0516e32f5ad03d5511abf7ff65297fc69705888617fbcb41d8c54801b91a8d1bfca7eba6d81d62ffe64c96749224f9f9866931ed8253454a2f89d9e16ce8e640feb505bdd312ed6266d9f2c9dfa25ae6e013b523c074c7203640f64cd7a2c0c8c79ac71c602f2dfbc713dcd39908f56c5749e796575655eb332792ce8018ea3a8eaa8bc5bc79015789a804d4fb4fde8ae394936addba24076a837eaa40b1381e73172218b2cc6982846fc853e5f34b911ab4d56eea0897fba718513770c7c1dd1ce591b51f5b4f4e41cd829e4c1645ab3c990be7c16f29f24f6387fe6910c9e00d42a079d4ac33c422f561057eb8a589b5d7c0bff8bd8c3a33cd589a1658cc626688b708216cfa247f3d922f8243d09d60b165a6b23a1365b571c45091c4208ed6c496ca54f8811e3644458a68a443611ba84b94efb5e2845a4a6ad2337c78438b2b9418ba3c103a90bd740df4168b28ddd0c8393d46e3eb629d2fd8aa522f95e74bdab65dd74296bf94f23f92a0429ad49eb53a9db9fd1f6517ddae1dc27f72d12530448ea98b3b8be73c93e3d9562a534aaeb8e0fefa468f28f2d0ba249eeda9899c46133cbfccd7e8acbbc031e4b71d9e298d6eb5da655c2248d390308a75a29aa873f1925c812d5acc3347ebd16398ddba0926541d6f54270f7cf09255b9700413a6fb9b1537e93b06a7d8b181b77da61a39b5ed313cec1239bf58ee149a941852164c6bd1d3247229b88577e79ee21b8302145f8042ec2bf6f925f8efa186e2161a78936776a95460d7982e17ce0fde1ec78270287991e648231d553f8a779b1600289e46bdc609ad4a62c7a1972be17e178a1e3b60f79a8f735e33435132f495a78a1e3b60f79a8f79fd15ff79ca68d7d6f731f7c484182444f2d0b388c43ec08a9cb18ac495b31d58d304c63e84c06bfd201ac1d82627db01df204511beda0d3f3fe454f7c93cfd44eb5f6d25217f6571df204511beda0d33b028230a85c1d4a777f366a5288b826cc2968203272744078fd7b4d2671dd0bd94b04978f4d4e3c5155a8162c84e82b88ef017de2369d33cc3f73dfebf565ebfd1f2be2391be976425e2dc8531efc6aa8de56195fe33f446c5673418036557131d553f8a779b1601cb873d27348f7c689329a8124593785c4addc235f23507ad7c98fff316276d7f3e9085a1671089cfc073285e940aadf62ca87e913b4ec02e78d90cd38a1ccec36ab73216ff4e16264f3049a8d533becf3b0925074dcceccfa72d8f2dace54434a9dd2ef9a6182f2aaf1d7bdd9ee0b349f9ebc3d17da79b0701e43140d7426148a9eaedf8f46a4785ae26d69d4f8379ae0f2854bbadb03c9361ab4107d94b80cbe8abcb4de34962b9068bfb415b9b253210eb5fe1bf65b4a738d8a0a2373c6897f2997b0255f3161ec6ce6921690b3bfea56f1b5368f782ee6b4559a5359e76860ebe9a94196c43c14b8a0001d5ca38df5ff30ff437cdd3ca6fe0374cd3c36ba615d91550addb6200cb8622338df9d8077265e7acb2cb8a4d6e2c6083cb680c8a44bdf82608e6765cb816bb11925e83cc8d6a979fb1fd564b76fdda27df885c0d4e260d203fe892de8f737c15dcbaf260080e03468eb9ba581bc43f7faf22c9c3c3ba4e8dcee111b54675ebc1e2b74d3f84077f0a6aa93a66ff4fff1c999f6aa06aa9b1f8aed9d34"));

    }
}