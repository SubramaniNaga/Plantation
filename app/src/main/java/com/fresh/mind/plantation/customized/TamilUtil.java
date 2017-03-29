package com.fresh.mind.plantation.customized;

/**
 * 
 * @author ????????
 * @???????? ?????????? ???????? ????????? ??????? ???????? ??????, ??????, ????, ???? ?????
 * ??????????????? ???????????? ???? ????? ?????????? ??????
 *
 */
public class TamilUtil {
	/**
	 * ?????? ???????? ?????? ???????????
	 */
	public final static int BAMINI = 0;
	/**
	 * ?????? ???????? ?????? ???????????
	 */
	public final static int TSCII = 1;
	/**
	 * ?????? ???????? ?????? ???????????
	 */
	public final static int ANJAL = 2;
	/**
	 * ???? ???????? ?????? ???????????
	 */
	public final static int TAB = 3;
	/**
	 * ???? ???????? ?????? ???????????
	 */
	public final static int TAM = 4;

	/**
	 * ???? method ??? ????? ??????? ?????????? ???????????? ??????? ????????????
	 * ??????? ???????????? ???????, ???????? ???????? ?????????.
	 * @param encodCode - ?????? ???????????? ????? ??????????????????? ?????? ????? ????????? ????????.
	 * ???????? ?????? ???? ??????? TamilUtil.BAMINI ????? ?????? ?????? ?????? ??????? TamilUtil.TSCII
	 * ????? ?????????????.
	 *
	 * @param unicodeStr ?????????? ??????? ????????
	 * @return ???????????? ??????? ??????? ??????????? ???????????? ??????????? ???????, ???????? ?????????? ????? ??????????.
	 */
	public static String convertToTamil(int encodCode, String unicodeStr){
		String convertedStr = "";
		TamilUtil tu = new TamilUtil();
		if(encodCode== TamilUtil.BAMINI){
			convertedStr = tu.convertToBamini(unicodeStr);
		}
		else if(encodCode== TamilUtil.TSCII){
			convertedStr = tu.convertToTSCII(unicodeStr);
		}
		else if(encodCode== TamilUtil.ANJAL){
			convertedStr = tu.convertToAnjal(unicodeStr);
		}
		else if(encodCode== TamilUtil.TAB){
			convertedStr = tu.convertToTab(unicodeStr);
		}
		else if(encodCode== TamilUtil.TAM){
			convertedStr = tu.convertToTam(unicodeStr);
		}
		return convertedStr;
	}

	private String convertToBamini(String unicodeStr){
		unicodeStr = unicodeStr.replace("????", "=");
		unicodeStr = unicodeStr.replace(",", ">");
		unicodeStr = unicodeStr.replace("??", "n[s");
		unicodeStr = unicodeStr.replace("??", "N[h");
		unicodeStr = unicodeStr.replace("??", "n[h");
		unicodeStr = unicodeStr.replace("??", "[h");
		unicodeStr = unicodeStr.replace("??", "[p");
		unicodeStr = unicodeStr.replace("??", "[P");
		unicodeStr = unicodeStr.replace("??", "[{");
		unicodeStr = unicodeStr.replace("??", "[_");
		unicodeStr = unicodeStr.replace("??", "n[");
		unicodeStr = unicodeStr.replace("??", "N[");
		unicodeStr = unicodeStr.replace("??", "i[");
		unicodeStr = unicodeStr.replace("??", "[;");
		unicodeStr = unicodeStr.replace("?", "[");
		unicodeStr = unicodeStr.replace("??", "nfs");
		unicodeStr = unicodeStr.replace("??", "Nfh");
		unicodeStr = unicodeStr.replace("??", "nfh");
		unicodeStr = unicodeStr.replace("??", "fh");
		unicodeStr = unicodeStr.replace("??", "fp");
		unicodeStr = unicodeStr.replace("??", "fP");
		unicodeStr = unicodeStr.replace("??", "F");
		unicodeStr = unicodeStr.replace("??", "$");
		unicodeStr = unicodeStr.replace("??", "nf");
		unicodeStr = unicodeStr.replace("??", "Nf");
		unicodeStr = unicodeStr.replace("??", "if");
		unicodeStr = unicodeStr.replace("??", "f;");
		unicodeStr = unicodeStr.replace("?", "f");
		unicodeStr = unicodeStr.replace("??", "nqs");
		unicodeStr = unicodeStr.replace("??", "Nqh");
		unicodeStr = unicodeStr.replace("??", "nqh");
		unicodeStr = unicodeStr.replace("??", "qh");
		unicodeStr = unicodeStr.replace("??", "qp");
		unicodeStr = unicodeStr.replace("??", "qP");
		unicodeStr = unicodeStr.replace("??", "*");
		unicodeStr = unicodeStr.replace("??", "*");
		unicodeStr = unicodeStr.replace("??", "nq");
		unicodeStr = unicodeStr.replace("??", "Nq");
		unicodeStr = unicodeStr.replace("??", "iq");
		unicodeStr = unicodeStr.replace("??", "q;");
		unicodeStr = unicodeStr.replace("?", "q");
		unicodeStr = unicodeStr.replace("??", "nrs");
		unicodeStr = unicodeStr.replace("??", "Nrh");
		unicodeStr = unicodeStr.replace("??", "nrh");
		unicodeStr = unicodeStr.replace("??", "rh");
		unicodeStr = unicodeStr.replace("??", "rp");
		unicodeStr = unicodeStr.replace("??", "rP");
		unicodeStr = unicodeStr.replace("??", "R");
		unicodeStr = unicodeStr.replace("??", "R+");
		unicodeStr = unicodeStr.replace("??", "nr");
		unicodeStr = unicodeStr.replace("??", "Nr");
		unicodeStr = unicodeStr.replace("??", "ir");
		unicodeStr = unicodeStr.replace("??", "r;");
		unicodeStr = unicodeStr.replace("?", "r");
		unicodeStr = unicodeStr.replace("??", "nQs");
		unicodeStr = unicodeStr.replace("??", "NQh");
		unicodeStr = unicodeStr.replace("??", "nQh");
		unicodeStr = unicodeStr.replace("??", "Qh");
		unicodeStr = unicodeStr.replace("??", "Qp");
		unicodeStr = unicodeStr.replace("??", "QP");
		unicodeStr = unicodeStr.replace("??", "*");
		unicodeStr = unicodeStr.replace("??", "*");
		unicodeStr = unicodeStr.replace("??", "nQ");
		unicodeStr = unicodeStr.replace("??", "NQ");
		unicodeStr = unicodeStr.replace("??", "iQ");
		unicodeStr = unicodeStr.replace("??", "Q;");
		unicodeStr = unicodeStr.replace("?", "Q");
		unicodeStr = unicodeStr.replace("??", "nls");
		unicodeStr = unicodeStr.replace("??", "Nlh");
		unicodeStr = unicodeStr.replace("??", "nlh");
		unicodeStr = unicodeStr.replace("??", "lh");
		unicodeStr = unicodeStr.replace("??", "b");
		unicodeStr = unicodeStr.replace("??", "B");
		unicodeStr = unicodeStr.replace("??", "L");
		unicodeStr = unicodeStr.replace("??", "^");
		unicodeStr = unicodeStr.replace("??", "nl");
		unicodeStr = unicodeStr.replace("??", "Nl");
		unicodeStr = unicodeStr.replace("??", "il");
		unicodeStr = unicodeStr.replace("??", "l;");
		unicodeStr = unicodeStr.replace("?", "l");
		unicodeStr = unicodeStr.replace("??", "nzs");
		unicodeStr = unicodeStr.replace("??", "Nzh");
		unicodeStr = unicodeStr.replace("??", "nzh");
		unicodeStr = unicodeStr.replace("??", "zh");
		unicodeStr = unicodeStr.replace("??", "zp");
		unicodeStr = unicodeStr.replace("??", "zP");
		unicodeStr = unicodeStr.replace("??", "Z");
		unicodeStr = unicodeStr.replace("??", "Z}");
		unicodeStr = unicodeStr.replace("??", "nz");
		unicodeStr = unicodeStr.replace("??", "Nz");
		unicodeStr = unicodeStr.replace("??", "iz");
		unicodeStr = unicodeStr.replace("??", "z;");
		unicodeStr = unicodeStr.replace("?", "z");
		unicodeStr = unicodeStr.replace("??", "njs");
		unicodeStr = unicodeStr.replace("??", "Njh");
		unicodeStr = unicodeStr.replace("??", "njh");
		unicodeStr = unicodeStr.replace("??", "jh");
		unicodeStr = unicodeStr.replace("??", "jp");
		unicodeStr = unicodeStr.replace("??", "jP");
		unicodeStr = unicodeStr.replace("??", "J");
		unicodeStr = unicodeStr.replace("??", "J}");
		unicodeStr = unicodeStr.replace("??", "nj");
		unicodeStr = unicodeStr.replace("??", "Nj");
		unicodeStr = unicodeStr.replace("??", "ij");
		unicodeStr = unicodeStr.replace("??", "j;");
		unicodeStr = unicodeStr.replace("?", "j");
		unicodeStr = unicodeStr.replace("??", "nes");
		unicodeStr = unicodeStr.replace("??", "Neh");
		unicodeStr = unicodeStr.replace("??", "neh");
		unicodeStr = unicodeStr.replace("??", "eh");
		unicodeStr = unicodeStr.replace("??", "ep");
		unicodeStr = unicodeStr.replace("??", "eP");
		unicodeStr = unicodeStr.replace("??", "E");
		unicodeStr = unicodeStr.replace("??", "E}");
		unicodeStr = unicodeStr.replace("??", "ne");
		unicodeStr = unicodeStr.replace("??", "Ne");
		unicodeStr = unicodeStr.replace("??", "ie");
		unicodeStr = unicodeStr.replace("??", "e;");
		unicodeStr = unicodeStr.replace("?", "e");
		unicodeStr = unicodeStr.replace("??", "nds");
		unicodeStr = unicodeStr.replace("??", "Ndh");
		unicodeStr = unicodeStr.replace("??", "ndh");
		unicodeStr = unicodeStr.replace("??", "dh");
		unicodeStr = unicodeStr.replace("??", "dp");
		unicodeStr = unicodeStr.replace("??", "dP");
		unicodeStr = unicodeStr.replace("??", "D");
		unicodeStr = unicodeStr.replace("??", "D}");
		unicodeStr = unicodeStr.replace("??", "nd");
		unicodeStr = unicodeStr.replace("??", "Nd");
		unicodeStr = unicodeStr.replace("??", "id");
		unicodeStr = unicodeStr.replace("??", "d;");
		unicodeStr = unicodeStr.replace("?", "d");
		unicodeStr = unicodeStr.replace("??", "ngs");
		unicodeStr = unicodeStr.replace("??", "Ngh");
		unicodeStr = unicodeStr.replace("??", "ngh");
		unicodeStr = unicodeStr.replace("??", "gh");
		unicodeStr = unicodeStr.replace("??", "gp");
		unicodeStr = unicodeStr.replace("??", "gP");
		unicodeStr = unicodeStr.replace("??", "G");
		unicodeStr = unicodeStr.replace("??", "G+");
		unicodeStr = unicodeStr.replace("??", "ng");
		unicodeStr = unicodeStr.replace("??", "Ng");
		unicodeStr = unicodeStr.replace("??", "ig");
		unicodeStr = unicodeStr.replace("??", "g;");
		unicodeStr = unicodeStr.replace("?", "g");
		unicodeStr = unicodeStr.replace("??", "nks");
		unicodeStr = unicodeStr.replace("??", "Nkh");
		unicodeStr = unicodeStr.replace("??", "nkh");
		unicodeStr = unicodeStr.replace("??", "kh");
		unicodeStr = unicodeStr.replace("??", "kp");
		unicodeStr = unicodeStr.replace("??", "kP");
		unicodeStr = unicodeStr.replace("??", "K");
		unicodeStr = unicodeStr.replace("??", "%");
		unicodeStr = unicodeStr.replace("??", "nk");
		unicodeStr = unicodeStr.replace("??", "Nk");
		unicodeStr = unicodeStr.replace("??", "ik");
		unicodeStr = unicodeStr.replace("??", "k;");
		unicodeStr = unicodeStr.replace("?", "k");
		unicodeStr = unicodeStr.replace("??", "nas");
		unicodeStr = unicodeStr.replace("??", "Nah");
		unicodeStr = unicodeStr.replace("??", "nah");
		unicodeStr = unicodeStr.replace("??", "ah");
		unicodeStr = unicodeStr.replace("??", "ap");
		unicodeStr = unicodeStr.replace("??", "aP");
		unicodeStr = unicodeStr.replace("??", "A");
		unicodeStr = unicodeStr.replace("??", "A+");
		unicodeStr = unicodeStr.replace("??", "na");
		unicodeStr = unicodeStr.replace("??", "Na");
		unicodeStr = unicodeStr.replace("??", "ia");
		unicodeStr = unicodeStr.replace("??", "a;");
		unicodeStr = unicodeStr.replace("?", "a");
		unicodeStr = unicodeStr.replace("??", "nus");
		unicodeStr = unicodeStr.replace("??", "Nuh");
		unicodeStr = unicodeStr.replace("??", "nuh");
		unicodeStr = unicodeStr.replace("??", "uh");
		unicodeStr = unicodeStr.replace("??", "up");
		unicodeStr = unicodeStr.replace("??", "uP");
		unicodeStr = unicodeStr.replace("??", "U");
		unicodeStr = unicodeStr.replace("??", "\\&");
		unicodeStr = unicodeStr.replace("??", "nu");
		unicodeStr = unicodeStr.replace("??", "Nu");
		unicodeStr = unicodeStr.replace("??", "iu");
		unicodeStr = unicodeStr.replace("??", "u;");
		unicodeStr = unicodeStr.replace("?", "u");
		unicodeStr = unicodeStr.replace("??", "nys");
		unicodeStr = unicodeStr.replace("??", "Nyh");
		unicodeStr = unicodeStr.replace("??", "nyh");
		unicodeStr = unicodeStr.replace("??", "yh");
		unicodeStr = unicodeStr.replace("??", "yp");
		unicodeStr = unicodeStr.replace("??", "yP");
		unicodeStr = unicodeStr.replace("??", "Y");
		unicodeStr = unicodeStr.replace("??", "Y}");
		unicodeStr = unicodeStr.replace("??", "ny");
		unicodeStr = unicodeStr.replace("??", "Ny");
		unicodeStr = unicodeStr.replace("??", "iy");
		unicodeStr = unicodeStr.replace("??", "y;");
		unicodeStr = unicodeStr.replace("?", "y");
		unicodeStr = unicodeStr.replace("??", "nss");
		unicodeStr = unicodeStr.replace("??", "Nsh");
		unicodeStr = unicodeStr.replace("??", "nsh");
		unicodeStr = unicodeStr.replace("??", "sh");
		unicodeStr = unicodeStr.replace("??", "sp");
		unicodeStr = unicodeStr.replace("??", "sP");
		unicodeStr = unicodeStr.replace("??", "S");
		unicodeStr = unicodeStr.replace("??", "Sh");
		unicodeStr = unicodeStr.replace("??", "ns");
		unicodeStr = unicodeStr.replace("??", "Ns");
		unicodeStr = unicodeStr.replace("??", "is");
		unicodeStr = unicodeStr.replace("??", "s;");
		unicodeStr = unicodeStr.replace("?", "s");
		unicodeStr = unicodeStr.replace("??", "nts");
		unicodeStr = unicodeStr.replace("??", "Nth");
		unicodeStr = unicodeStr.replace("??", "nth");
		unicodeStr = unicodeStr.replace("??", "th");
		unicodeStr = unicodeStr.replace("??", "tp");
		unicodeStr = unicodeStr.replace("??", "tP");
		unicodeStr = unicodeStr.replace("??", "T");
		unicodeStr = unicodeStr.replace("??", "T+");
		unicodeStr = unicodeStr.replace("??", "nt");
		unicodeStr = unicodeStr.replace("??", "Nt");
		unicodeStr = unicodeStr.replace("??", "it");
		unicodeStr = unicodeStr.replace("??", "t;");
		unicodeStr = unicodeStr.replace("?", "t");
		unicodeStr = unicodeStr.replace("??", "nos");
		unicodeStr = unicodeStr.replace("??", "Noh");
		unicodeStr = unicodeStr.replace("??", "noh");
		unicodeStr = unicodeStr.replace("??", "oh");
		unicodeStr = unicodeStr.replace("??", "op");
		unicodeStr = unicodeStr.replace("??", "oP");
		unicodeStr = unicodeStr.replace("??", "O");
		unicodeStr = unicodeStr.replace("??", "*");
		unicodeStr = unicodeStr.replace("??", "no");
		unicodeStr = unicodeStr.replace("??", "No");
		unicodeStr = unicodeStr.replace("??", "io");
		unicodeStr = unicodeStr.replace("??", "o;");
		unicodeStr = unicodeStr.replace("?", "o");
		unicodeStr = unicodeStr.replace("??", "nws");
		unicodeStr = unicodeStr.replace("??", "Nwh");
		unicodeStr = unicodeStr.replace("??", "nwh");
		unicodeStr = unicodeStr.replace("??", "wh");
		unicodeStr = unicodeStr.replace("??", "wp");
		unicodeStr = unicodeStr.replace("??", "wP");
		unicodeStr = unicodeStr.replace("??", "W");
		unicodeStr = unicodeStr.replace("??", "W}");
		unicodeStr = unicodeStr.replace("??", "nw");
		unicodeStr = unicodeStr.replace("??", "Nw");
		unicodeStr = unicodeStr.replace("??", "iw");
		unicodeStr = unicodeStr.replace("??", "w;");
		unicodeStr = unicodeStr.replace("?", "w");
		unicodeStr = unicodeStr.replace("??", "n`s");
		unicodeStr = unicodeStr.replace("??", "N`h");
		unicodeStr = unicodeStr.replace("??", "n`h");
		unicodeStr = unicodeStr.replace("??", "`h");
		unicodeStr = unicodeStr.replace("??", "`p");
		unicodeStr = unicodeStr.replace("??", "`P");
		unicodeStr = unicodeStr.replace("??", "{`");
		unicodeStr = unicodeStr.replace("??", "`_");
		unicodeStr = unicodeStr.replace("??", "n`");
		unicodeStr = unicodeStr.replace("??", "N`");
		unicodeStr = unicodeStr.replace("??", "i`");
		unicodeStr = unicodeStr.replace("??", "`;");
		unicodeStr = unicodeStr.replace("?", "`");
		unicodeStr = unicodeStr.replace("??", "n\\s");
		unicodeStr = unicodeStr.replace("??", "N\\h");
		unicodeStr = unicodeStr.replace("??", "n\\h");
		unicodeStr = unicodeStr.replace("??", "\\h");
		unicodeStr = unicodeStr.replace("??", "\\p");
		unicodeStr = unicodeStr.replace("??", "\\P");
		unicodeStr = unicodeStr.replace("??", "\\{");
		unicodeStr = unicodeStr.replace("??", "\\\\_");
		unicodeStr = unicodeStr.replace("??", "n\\");
		unicodeStr = unicodeStr.replace("??", "N\\");
		unicodeStr = unicodeStr.replace("??", "i\\");
		unicodeStr = unicodeStr.replace("??", "\\;");
		unicodeStr = unicodeStr.replace("?", "\\");
		unicodeStr = unicodeStr.replace("??", "n]s");
		unicodeStr = unicodeStr.replace("??", "N]h");
		unicodeStr = unicodeStr.replace("??", "n]h");
		unicodeStr = unicodeStr.replace("??", "]h");
		unicodeStr = unicodeStr.replace("??", "]p");
		unicodeStr = unicodeStr.replace("??", "]P");
		unicodeStr = unicodeStr.replace("??", "]{");
		unicodeStr = unicodeStr.replace("??", "]_");
		unicodeStr = unicodeStr.replace("??", "n]");
		unicodeStr = unicodeStr.replace("??", "N]");
		unicodeStr = unicodeStr.replace("??", "i]");
		unicodeStr = unicodeStr.replace("??", "];");
		unicodeStr = unicodeStr.replace("?", "]");
		unicodeStr = unicodeStr.replace("?", "m");
		unicodeStr = unicodeStr.replace("?", "M");
		unicodeStr = unicodeStr.replace("?", ",");
		unicodeStr = unicodeStr.replace("?", "<");
		unicodeStr = unicodeStr.replace("?", "c");
		unicodeStr = unicodeStr.replace("?", "C");
		unicodeStr = unicodeStr.replace("?", "v");
		unicodeStr = unicodeStr.replace("?", "V");
		unicodeStr = unicodeStr.replace("?", "I");
		unicodeStr = unicodeStr.replace("?", "x");
		unicodeStr = unicodeStr.replace("?", "X");
		unicodeStr = unicodeStr.replace("?", "xs");
		unicodeStr = unicodeStr.replace("?", "\"");

		return unicodeStr;
	}

	private String convertToTSCII(String unicodeStr){
		unicodeStr = unicodeStr.replace("??", "���");
		unicodeStr = unicodeStr.replace("??", "���");
		unicodeStr = unicodeStr.replace("??", "���");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "\\�");
		unicodeStr = unicodeStr.replace("?", "�");
		unicodeStr = unicodeStr.replace("??", "���");
		unicodeStr = unicodeStr.replace("??", "���");
		unicodeStr = unicodeStr.replace("??", "���");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "�");
		unicodeStr = unicodeStr.replace("??", "�");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "�");
		unicodeStr = unicodeStr.replace("?", "�");
		unicodeStr = unicodeStr.replace("??", "���");
		unicodeStr = unicodeStr.replace("??", "���");
		unicodeStr = unicodeStr.replace("??", "���");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "?");
		unicodeStr = unicodeStr.replace("??", "?");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "�");
		unicodeStr = unicodeStr.replace("?", "�");
		unicodeStr = unicodeStr.replace("??", "���");
		unicodeStr = unicodeStr.replace("??", "���");
		unicodeStr = unicodeStr.replace("??", "���");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "�");
		unicodeStr = unicodeStr.replace("??", "�");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "�");
		unicodeStr = unicodeStr.replace("?", "�");
		unicodeStr = unicodeStr.replace("??", "���");
		unicodeStr = unicodeStr.replace("??", "���");
		unicodeStr = unicodeStr.replace("??", "���");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "?");
		unicodeStr = unicodeStr.replace("??", "?");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "�");
		unicodeStr = unicodeStr.replace("?", "�");
		unicodeStr = unicodeStr.replace("??", "���");
		unicodeStr = unicodeStr.replace("??", "���");
		unicodeStr = unicodeStr.replace("??", "���");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "�");
		unicodeStr = unicodeStr.replace("??", "�");
		unicodeStr = unicodeStr.replace("??", "�");
		unicodeStr = unicodeStr.replace("??", "�");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "�");
		unicodeStr = unicodeStr.replace("?", "�");
		unicodeStr = unicodeStr.replace("??", "���");
		unicodeStr = unicodeStr.replace("??", "���");
		unicodeStr = unicodeStr.replace("??", "���");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "�");
		unicodeStr = unicodeStr.replace("??", "�");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "�");
		unicodeStr = unicodeStr.replace("?", "�");
		unicodeStr = unicodeStr.replace("??", "���");
		unicodeStr = unicodeStr.replace("??", "���");
		unicodeStr = unicodeStr.replace("??", "���");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "�");
		unicodeStr = unicodeStr.replace("??", "�");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "�");
		unicodeStr = unicodeStr.replace("?", "�");
		unicodeStr = unicodeStr.replace("??", "���");
		unicodeStr = unicodeStr.replace("??", "���");
		unicodeStr = unicodeStr.replace("??", "���");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "�");
		unicodeStr = unicodeStr.replace("??", "�");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "�");
		unicodeStr = unicodeStr.replace("?", "�");
		unicodeStr = unicodeStr.replace("??", "�ɪ");
		unicodeStr = unicodeStr.replace("??", "�ɡ");
		unicodeStr = unicodeStr.replace("??", "�ɡ");
		unicodeStr = unicodeStr.replace("??", "ɡ");
		unicodeStr = unicodeStr.replace("??", "ɢ");
		unicodeStr = unicodeStr.replace("??", "ɣ");
		unicodeStr = unicodeStr.replace("??", "�");
		unicodeStr = unicodeStr.replace("??", "�");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "�");
		unicodeStr = unicodeStr.replace("?", "�");
		unicodeStr = unicodeStr.replace("??", "���");
		unicodeStr = unicodeStr.replace("??", "���");
		unicodeStr = unicodeStr.replace("??", "���");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "�");
		unicodeStr = unicodeStr.replace("??", "�");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "�");
		unicodeStr = unicodeStr.replace("?", "�");
		unicodeStr = unicodeStr.replace("??", "���");
		unicodeStr = unicodeStr.replace("??", "���");
		unicodeStr = unicodeStr.replace("??", "���");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "�");
		unicodeStr = unicodeStr.replace("??", "�");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "�");
		unicodeStr = unicodeStr.replace("?", "�");
		unicodeStr = unicodeStr.replace("??", "�ª");
		unicodeStr = unicodeStr.replace("??", "�¡");
		unicodeStr = unicodeStr.replace("??", "�¡");
		unicodeStr = unicodeStr.replace("??", "¡");
		unicodeStr = unicodeStr.replace("??", "¢");
		unicodeStr = unicodeStr.replace("??", "£");
		unicodeStr = unicodeStr.replace("??", "�");
		unicodeStr = unicodeStr.replace("??", "�");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "�");
		unicodeStr = unicodeStr.replace("?", "�");
		unicodeStr = unicodeStr.replace("??", "�ê");
		unicodeStr = unicodeStr.replace("??", "�á");
		unicodeStr = unicodeStr.replace("??", "�á");
		unicodeStr = unicodeStr.replace("??", "á");
		unicodeStr = unicodeStr.replace("??", "â");
		unicodeStr = unicodeStr.replace("??", "ã");
		unicodeStr = unicodeStr.replace("??", "�");
		unicodeStr = unicodeStr.replace("??", "�");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "�");
		unicodeStr = unicodeStr.replace("?", "�");
		unicodeStr = unicodeStr.replace("??", "�Ī");
		unicodeStr = unicodeStr.replace("??", "�ġ");
		unicodeStr = unicodeStr.replace("??", "�ġ");
		unicodeStr = unicodeStr.replace("??", "ġ");
		unicodeStr = unicodeStr.replace("??", "Ģ");
		unicodeStr = unicodeStr.replace("??", "ģ");
		unicodeStr = unicodeStr.replace("??", "�");
		unicodeStr = unicodeStr.replace("??", "�");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "�");
		unicodeStr = unicodeStr.replace("?", "�");
		unicodeStr = unicodeStr.replace("??", "�Ǫ");
		unicodeStr = unicodeStr.replace("??", "�ǡ");
		unicodeStr = unicodeStr.replace("??", "�ǡ");
		unicodeStr = unicodeStr.replace("??", "ǡ");
		unicodeStr = unicodeStr.replace("??", "Ǣ");
		unicodeStr = unicodeStr.replace("??", "ǣ");
		unicodeStr = unicodeStr.replace("??", "�");
		unicodeStr = unicodeStr.replace("??", "�");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "�");
		unicodeStr = unicodeStr.replace("?", "�");
		unicodeStr = unicodeStr.replace("??", "�Ū");
		unicodeStr = unicodeStr.replace("??", "�š");
		unicodeStr = unicodeStr.replace("??", "�š");
		unicodeStr = unicodeStr.replace("??", "š");
		unicodeStr = unicodeStr.replace("??", "Ţ");
		unicodeStr = unicodeStr.replace("??", "ţ");
		unicodeStr = unicodeStr.replace("??", "�");
		unicodeStr = unicodeStr.replace("??", "�");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "�");
		unicodeStr = unicodeStr.replace("?", "�");
		unicodeStr = unicodeStr.replace("??", "�ƪ");
		unicodeStr = unicodeStr.replace("??", "�ơ");
		unicodeStr = unicodeStr.replace("??", "�ơ");
		unicodeStr = unicodeStr.replace("??", "ơ");
		unicodeStr = unicodeStr.replace("??", "Ƣ");
		unicodeStr = unicodeStr.replace("??", "ƣ");
		unicodeStr = unicodeStr.replace("??", "�");
		unicodeStr = unicodeStr.replace("??", "�");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "�");
		unicodeStr = unicodeStr.replace("?", "�");
		unicodeStr = unicodeStr.replace("??", "�Ȫ");
		unicodeStr = unicodeStr.replace("??", "�ȡ");
		unicodeStr = unicodeStr.replace("??", "�ȡ");
		unicodeStr = unicodeStr.replace("??", "ȡ");
		unicodeStr = unicodeStr.replace("??", "Ȣ");
		unicodeStr = unicodeStr.replace("??", "ȣ");
		unicodeStr = unicodeStr.replace("??", "�");
		unicodeStr = unicodeStr.replace("??", "�");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "�");
		unicodeStr = unicodeStr.replace("?", "�");
		unicodeStr = unicodeStr.replace("??", "�?�");
		unicodeStr = unicodeStr.replace("??", "�?�");
		unicodeStr = unicodeStr.replace("??", "�?�");
		unicodeStr = unicodeStr.replace("??", "?��");
		unicodeStr = unicodeStr.replace("??", "?�");
		unicodeStr = unicodeStr.replace("??", "?�");
		unicodeStr = unicodeStr.replace("??", "?�");
		unicodeStr = unicodeStr.replace("??", "?�");
		unicodeStr = unicodeStr.replace("??", "�?");
		unicodeStr = unicodeStr.replace("??", "�?");
		unicodeStr = unicodeStr.replace("??", "�?");
		unicodeStr = unicodeStr.replace("??", "?� ");
		unicodeStr = unicodeStr.replace("?", "�?");
		unicodeStr = unicodeStr.replace("??", "���");
		unicodeStr = unicodeStr.replace("??", "���");
		unicodeStr = unicodeStr.replace("??", "���");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "�");
		unicodeStr = unicodeStr.replace("?", "�");
		unicodeStr = unicodeStr.replace("??", "���");
		unicodeStr = unicodeStr.replace("??", "���");
		unicodeStr = unicodeStr.replace("??", "���");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "��");
		unicodeStr = unicodeStr.replace("??", "�");
		unicodeStr = unicodeStr.replace("?", "�");
		unicodeStr = unicodeStr.replace("?", "�");
		unicodeStr = unicodeStr.replace("?", "�");
		unicodeStr = unicodeStr.replace("?", "�");
		unicodeStr = unicodeStr.replace("?", "�");
		unicodeStr = unicodeStr.replace("?", "�");
		unicodeStr = unicodeStr.replace("?", "�");
		unicodeStr = unicodeStr.replace("?", "�");
		unicodeStr = unicodeStr.replace("?", "�");
		unicodeStr = unicodeStr.replace("?", "�");
		unicodeStr = unicodeStr.replace("?", "�");
		unicodeStr = unicodeStr.replace("?", "�");
		unicodeStr = unicodeStr.replace("?", "�");
		unicodeStr = unicodeStr.replace("?", "�");
		unicodeStr = unicodeStr.replace("????", "?");
		unicodeStr = unicodeStr.replace("�", "�");
		unicodeStr = unicodeStr.replace("?", "?");
		unicodeStr = unicodeStr.replace("?", "?");
		unicodeStr = unicodeStr.replace("?", "?");
		unicodeStr = unicodeStr.replace("?", "?");
		unicodeStr = unicodeStr.replace("?", "?");
		unicodeStr = unicodeStr.replace("?", "?");
		unicodeStr = unicodeStr.replace("?", "?");
		unicodeStr = unicodeStr.replace("?", "�");
		unicodeStr = unicodeStr.replace("?", "�");
		unicodeStr = unicodeStr.replace("?", "�");
		unicodeStr = unicodeStr.replace("?", "�");
		unicodeStr = unicodeStr.replace("?", "�");
		unicodeStr = unicodeStr.replace("?", "�");

		return unicodeStr;
	}

	private String convertToAnjal(String unicodeStr){
		String TamilText[] = { "�", "�", "�", "�", "�", "�", "��", "��", "��", "���",
				"���", "?�", "�", "���", "��", "��", "�", "�?", "�", "�?",
				"�", "�", "��", "��", "��", "�", "�", "�?�", "�?�", "�?�",
				"?�", "�", "?", "?", "�", "�?", "�?", "�?", "�",
				"?", "���", "���", "���", "��", "�?", "�?", "��", "��", "��",
				"�", "�", "���", "���", "���", "��", "?", "?", "�", "�",
				"��", "��", "��", "�", "�", "�", "���", "���", "���", "��",
				"�?", "�?", "��", "��", "��", "�", "�", "���", "���", "���",
				"��", "�", "�", "�", "�", "��", "��", "��", "�", "�",
				"���", "���", "���", "��", "�", "?", "��", "�", "��", "��",
				"��", "�", "�", "���", "���", "���", "��", "�", "�", "��",
				"�", "��", "��", "��", "�", "�", "���", "���", "���", "��",
				"�", "�", "��", "�", "��", "��", "��", "�", "�", "���",
				"��", "��", "�", "�", "�", "�", "�", "��", "��", "��",
				"�", "�", "���", "���", "���", "��", "�", "�", "�", "�",
				"��", "��", "��", "�", "�", "���", "���", "���", "��", "�",
				"�", "�", "�", "��", "��", "��", "�", "�", "���", "�Ƒ",
				"�Ƒ", "Ƒ", "�", "�", "�", "�", "��", "��", "��", "�",
				"�", "���", "�̑", "�̑", "̑", "�", "�", "�", "�", "��",
				"��", "��", "�", "�", "���", "�ґ", "�ґ", "ґ", "�", "�",
				"Ֆ", "�", "��", "��", "��", "�", "�", "���", "��", "��",
				"�", "�", "�", "�", "�", "��", "��", "��", "�", "�",
				"��", "���", "�ב", "�ב", "ב", "�", "�", "�", "�", "��",
				"��", "�", "�", "���", "��", "��", "�", "�", "�", "�",
				"�", "��", "��", "��", "�", "�", "���", "��", "��", "�",
				"�?", "�?", "�", "�", "��", "��", "��", "�", "�", "���",
				"�ݑ", "�ݑ", "ݑ", "�", "�", "�", "�", "��", "��", "��",
				"�", "�", "���", "���", "���", "��", "�", "�", "��", "��",
				"��", "��", "��", "�", "�", "���", "���", "���", "��", "�?",
				"�?", "��", "��", "��", "�", "�", "���", "���", "���", "��",
				"�?", "�?", "��", "��", "��", "�", "�", "?", "�", "�",
				"�", "�", "�", "�", "�", "�", "�", "��", "�", "�",
				"�"};

		String TamilReplace[] = {"???", "????", "????", "????", "????", "????", "????", "????", "????", "????",
				"????", "????", "????", "??", "??", "??", "??", "??", "??", "??",
				"??", "??", "??", "??", "??", "??", "?", "??", "??", "??",
				"??", "??", "??", "??", "??", "??", "??", "??", "??",
				"?", "??", "??", "??", "??", "??", "??", "??", "??", "??",
				"??", "?", "??", "??", "??", "??", "??", "??", "??", "??",
				"??", "??", "??", "??", "?", "?", "??", "??", "??", "??",
				"??", "??", "??", "??", "??", "??", "?", "??", "??", "??",
				"??", "??", "??", "??", "??", "??", "??", "??", "??", "?",
				"??", "??", "??", "??", "??", "??", "??", "??", "??", "??",
				"??", "??", "?", "??", "??", "??", "??", "??", "??", "??",
				"??", "??", "??", "??", "??", "?", "??", "??", "??", "??",
				"??", "??", "??", "??", "??", "??", "??", "??", "?", "??",
				"??", "??", "??", "??", "??", "??", "??", "??", "??", "??",
				"??", "?", "??", "??", "??", "??", "??", "??", "??", "??",
				"??", "??", "??", "??", "?", "??", "??", "??", "??", "??",
				"??", "??", "??", "??", "??", "??", "??", "?", "??", "??",
				"??", "??", "??", "??", "??", "??", "??", "??", "??", "??",
				"?", "??", "??", "??", "??", "??", "??", "??", "??", "??",
				"??", "??", "??", "?", "??", "??", "??", "??", "??", "??",
				"??", "??", "??", "??", "??", "??", "?", "??", "??", "??",
				"??", "??", "??", "??", "??", "??", "??", "??", "??", "?",
				"??", "??", "??", "??", "??", "??", "??", "??", "??", "??",
				"??", "??", "?", "??", "??", "??", "??", "??", "??", "??",
				"??", "??", "??", "??", "??", "?", "??", "??", "??", "??",
				"??", "??", "??", "??", "??", "??", "??", "??", "?", "??",
				"??", "??", "??", "??", "??", "??", "??", "??", "??", "??",
				"??", "?", "??", "??", "??", "??", "??", "??", "??", "??",
				"??", "??", "??", "??", "?", "??", "??", "??", "??", "??",
				"??", "??", "??", "??", "??", "?", "??", "??", "??", "??",
				"??", "??", "??", "??", "??", "??", "?", "?", "?", "?",
				"?", "?", "?", "?", "?", "?", "?", "?", "?", "?",
				"????"};

		int count = 0;

		while (count < TamilText.length) {
			unicodeStr = unicodeStr.replaceAll(TamilReplace[count],TamilText[count]);
			count++;
		}
		return unicodeStr;
	}


	private String convertToTab(String unicodeStr){
		String TamilText[] = {"�", "��", "��", "��", "��", "��", "��", "��", "���", "���",
				"���", "��", "��", "���", "��", "��", "�", "�", "�", "�",
				"�", "��", "��", "��", "�", "�", "���", "��", "��", "�",
				"�", "�", "�", "�", "��", "��", "��", "�", "�", "���",
				"��", "��", "�", "�", "�", "�", "�", "��", "��", "��",
				"�", "�", "���", "��", "��", "�", "�", "�", "�", "�",
				"��", "��", "��", "�", "�", "���", "��", "��", "�", "�",
				"�", "�", "�", "��", "��", "��", "�", "�", "���", "��",
				"��", "�", "�", "�", "�", "�", "�", "�", "��", "��",
				"��", "�", "�", "���", "��", "��", "�", "�", "�", "�",
				"�", "��", "��", "��", "�", "�", "���", "��", "��", "�",
				"�", "�", "�", "�", "��", "��", "��", "�", "�", "���",
				"��", "��", "�", "�", "�", "�", "�", "��", "��", "��",
				"�", "�", "���", "��", "��", "�", "�", "�", "�", "�",
				"��", "��", "��", "�", "�", "���", "��", "��", "�", "�",
				"�", "�", "�", "��", "��", "��", "�", "�", "���", "��",
				"��", "�", "�", "�", "�", "�", "��", "��", "��", "�",
				"�", "���", "��", "��", "�", "�", "�", "�", "�", "��",
				"��", "��", "�", "�", "���", "��", "��", "�", "�", "�",
				"�", "�", "��", "��", "��", "�", "�", "���", "���", "���",
				"��", "��", "��", "�", "�", "��", "��", "��", "��", "�",
				"���", "���", "���", "��", "��", "��", "�", "�", "��", "��",
				"��", "��", "�", "���", "���", "���", "��", "��", "��", "�",
				"�", "��", "��", "��", "��", "�", "���", "���", "���", "��",
				"��", "��", "�", "�", "��", "��", "��", "��", "�", "���",
				"��", "��", "�", "�", "�", "�", "�", "��", "��", "��",
				"�", "�", "���", "��", "��", "�", "�", "�", "��", "��",
				"��", "�", "�", "���", "��", "��", "�", "�", "�", "��",
				"��", "��", "�", "�", "���", "��", "��", "�", "�", "�",
				"��", "��", "��", "�", "�", "�", "�", "�", "�", "�",
				"�", "�", "�", "�", "�", "�", "��", "�", "�", "�",
				"�", "�", "�", "�", "�", "�", "?", "�", "�", "�",
				"�"};

		String TamilReplace[] = {"???", "????", "????", "????", "????", "????", "????", "????", "????", "????",
				"????", "????", "????", "??", "??", "??", "??", "??", "??", "??",
				"??", "??", "??", "??", "??", "?", "??", "??", "??", "??",
				"??", "??", "??", "??", "??", "??", "??", "??", "?", "??",
				"??", "??", "??", "??", "??", "??", "??", "??", "??", "??",
				"??", "?", "??", "??", "??", "??", "??", "??", "??", "??",
				"??", "??", "??", "??", "?", "??", "??", "??", "??", "??",
				"??", "??", "??", "??", "??", "??", "??", "?", "??", "??",
				"??", "??", "??", "??", "??", "??", "??", "??", "??", "??",
				"??", "??", "?", "??", "??", "??", "??", "??", "??", "??",
				"??", "??", "??", "??", "??", "?", "??", "??", "??", "??",
				"??", "??", "??", "??", "??", "??", "??", "??", "?", "??",
				"??", "??", "??", "??", "??", "??", "??", "??", "??", "??",
				"??", "?", "??", "??", "??", "??", "??", "??", "??", "??",
				"??", "??", "??", "??", "?", "??", "??", "??", "??", "??",
				"??", "??", "??", "??", "??", "??", "??", "?", "??", "??",
				"??", "??", "??", "??", "??", "??", "??", "??", "??", "??",
				"?", "??", "??", "??", "??", "??", "??", "??", "??", "??",
				"??", "??", "??", "?", "??", "??", "??", "??", "??", "??",
				"??", "??", "??", "??", "??", "??", "?", "??", "??", "??",
				"??", "??", "??", "??", "??", "??", "??", "??", "??", "?",
				"??", "??", "??", "??", "??", "??", "??", "??", "??", "??",
				"??", "??", "?", "??", "??", "??", "??", "??", "??", "??",
				"??", "??", "??", "??", "??", "?", "??", "??", "??", "??",
				"??", "??", "??", "??", "??", "??", "??", "??", "?", "??",
				"??", "??", "??", "??", "??", "??", "??", "??", "??", "??",
				"??", "?", "??", "??", "??", "??", "??", "??", "??", "??",
				"??", "??", "?", "??", "??", "??", "??", "??", "??", "??",
				"??", "??", "??", "?", "??", "??", "??", "??", "??", "??",
				"??", "??", "??", "??", "?", "?", "?", "?", "?", "?",
				"?", "?", "?", "?", "?", "?", "?", "?", "?", "?",
				"?", "?", "?", "?", "?", "?", "?", "?", "?", "?",
				"????"};

		int count = 0;
		while (count < TamilText.length) {
			unicodeStr = unicodeStr.replaceAll(TamilReplace[count],TamilText[count]);
			count++;
		}

		return unicodeStr;
	}

	private String convertToTam(String unicodeStr){
		String TamilText[] = {"�", "��", "��", "V", "r", "��", "��", "��", "��", "���",
				"���", "���", "z", "���", "��", "��", "�", "T", "p", "�",
				"�", "��", "��", "��", "��", "x", "�", "���", "��", "��",
				"�", "A", "W", "�", "�", "��", "��", "��", "�", "�",
				"���", "��", "��", "�", "B", "X", "�", "�", "��", "��",
				"��", "�", "�", "���", "��", "��", "�", "C", "Y", "�",
				"�", "��", "��", "��", "�", "�", "���", "��", "��", "�",
				"D", "Z", "�", "�", "��", "��", "��", "�", "�", "���",
				"��", "��", "�", "�", "�", "�", "�", "��", "��", "��",
				"�", "�", "���", "��", "��", "�", "E", "a", "�", "�",
				"��", "��", "��", "�", "�", "���", "��", "��", "�", "F",
				"b", "�", "�", "��", "��", "��", "�", "�", "���", "��",
				"��", "�", "G", "c", "�", "�", "��", "��", "��", "��",
				"�", "�", "���", "��", "��", "�", "Q", "m", "�", "�",
				"��", "��", "��", "��", "�", "�", "���", "��", "��", "�",
				"H", "d", "�", "�", "��", "��", "��", "��", "�", "�",
				"���", "��", "��", "�", "I", "e", "�", "�", "��", "��",
				"��", "��", "�", "�", "���", "��", "��", "�", "J", "f",
				"�", "�", "��", "��", "��", "��", "�", "�", "���", "��",
				"��", "�", "K", "g", "�", "�", "��", "��", "��", "�",
				"�", "���", "���", "���", "��", "L", "h", "�", "�", "��",
				"��", "��", "�", "�", "���", "���", "���", "��", "O", "k",
				"�", "�", "��", "��", "��", "�", "�", "���", "���", "���",
				"��", "M", "i", "�", "�", "��", "��", "��", "���", "�",
				"�", "���", "���", "���", "��", "N", "j", "�", "�", "��",
				"��", "��", "�", "�", "���", "��", "��", "�", "P", "l",
				"�", "�", "��", "��", "��", "��", "�", "�", "���", "��",
				"��", "�", "U", "q", "��", "��", "��", "y", "�", "���",
				"��", "��", "�", "S", "o", "��", "��", "��", "��", "w",
				"�", "���", "��", "��", "�", "R", "n", "��", "��", "��",
				"v", "�", "�", "�", "�", "�", "�", "�", "�", "�",
				"�", "�", "�", "��", "�", "�", "�"};

		String TamilReplace[] = {"???", "????", "????", "????", "????", "????", "????", "????", "????", "????",
				"????", "????", "????", "??", "??", "??", "??", "??", "??", "??",
				"??", "??", "??", "??", "??", "??", "?", "??", "??", "??",
				"??", "??", "??", "??", "??", "??", "??", "??", "??", "?",
				"??", "??", "??", "??", "??", "??", "??", "??", "??", "??",
				"??", "??", "?", "??", "??", "??", "??", "??", "??", "??",
				"??", "??", "??", "??", "??", "?", "??", "??", "??", "??",
				"??", "??", "??", "??", "??", "??", "??", "??", "?", "??",
				"??", "??", "??", "??", "??", "??", "??", "??", "??", "??",
				"??", "?", "??", "??", "??", "??", "??", "??", "??", "??",
				"??", "??", "??", "??", "?", "??", "??", "??", "??", "??",
				"??", "??", "??", "??", "??", "??", "??", "?", "??", "??",
				"??", "??", "??", "??", "??", "??", "??", "??", "??", "??",
				"??", "?", "??", "??", "??", "??", "??", "??", "??", "??",
				"??", "??", "??", "??", "??", "?", "??", "??", "??", "??",
				"??", "??", "??", "??", "??", "??", "??", "??", "??", "?",
				"??", "??", "??", "??", "??", "??", "??", "??", "??", "??",
				"??", "??", "??", "?", "??", "??", "?", "??", "??", "??",
				"??", "??", "??", "??", "??", "??", "??", "?", "??", "??",
				"??", "??", "??", "??", "??", "??", "??", "??", "??", "??",
				"?", "??", "??", "??", "??", "??", "??", "??", "??", "??",
				"??", "??", "??", "?", "??", "??", "?", "??", "??", "??",
				"??", "??", "??", "??", "??", "??", "?", "??", "??", "??",
				"??", "??", "??", "??", "??", "??", "??", "??", "??", "??",
				"?", "??", "??", "??", "??", "??", "??", "??", "??", "??",
				"??", "??", "??", "?", "??", "??", "??", "??", "??", "??",
				"??", "??", "??", "??", "??", "??", "??", "?", "??", "??",
				"??", "??", "??", "??", "??", "??", "??", "??", "?", "??",
				"??", "??", "??", "??", "??", "??", "??", "??", "??", "??",
				"?", "??", "??", "??", "??", "??", "??", "??", "??", "??",
				"??", "?", "?", "?", "?", "?", "?", "?", "?", "?",
				"?", "?", "?", "?", "?", "????", "�"};

		int count = 0;
		while (count < TamilText.length) {
			unicodeStr = unicodeStr.replaceAll(TamilReplace[count], TamilText[count]);
			count++;
		}
		return unicodeStr;
		
	}
}
