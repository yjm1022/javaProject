package com.qq.base;

public interface Cmd {
		//����֪ͨ����¼)
		public static final int CMD_ONLINE	=1000;
		//����
		public static final int CMD_OFFLINE	=1001;
		//����
		public static final int CMD_LEVEL	=1002;
		//æµ
		public static final int CMD_BUYS	=1003;
		//������Ϣ
		public static final int CMD_SEND	=1004;
		//�����ļ�
		public static final int CMD_FILE	=1005;
		//����
		public static final int CMD_SHAKE	=1006;
		//��Ӻ���
		public static final int CMD_ADDFRI	=1007;
		//ͬ����Ӻ���
		public static final int CMD_ARGEE	=1008;
		//�ܾ���Ӻ���
		public static final int CMD_REFUSE	=1009;
		//����״̬
		public static final int CMD_CHANGESTATE	=1010;
		//ɾ������
		public static final int CMD_DELFRIEND	=1011;
		
		//����״̬
		public static final String[] STATUS={"����","����","æµ","����"};
		//��������
		public static final String[] GROUPNAME={"����","ͬ��","����","������"};
		
}
