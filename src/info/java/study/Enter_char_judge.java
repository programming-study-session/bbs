package info.java.study;

public class Enter_char_judge {

	static String Enter_char_replace(String Enter_char) {

		//�S�p,���p�X�y�[�X���폜
		String Enter_char_replace = null;
		Enter_char_replace = Enter_char.replaceAll("�@","");
		Enter_char_replace = Enter_char_replace.replaceAll(" ","");
		Enter_char_replace = Enter_char_replace.replaceAll("\n","");
		return Enter_char_replace;
	}

}