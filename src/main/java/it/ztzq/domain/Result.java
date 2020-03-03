package it.ztzq.domain;

public class Result {
    private String key;
    private String msgvalue;
    private String rmsgvalue;
    //用来判断是否相等, 0表示两边参数一致，
    //               1表示msg中有,rmsg中没有
    //               2表示msg中没有，rmsg中有
    //               3表示两边都有但是值不一样
    private int flag;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMsgvalue() {
        return msgvalue;
    }

    public void setMsgvalue(String msgvalue) {
        this.msgvalue = msgvalue;
    }

    public String getRmsgvalue() {
        return rmsgvalue;
    }

    public void setRmsgvalue(String rmsgvalue) {
        this.rmsgvalue = rmsgvalue;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "Result{" +
                "key='" + key + '\'' +
                ", msgvalue='" + msgvalue + '\'' +
                ", rmsgvalue='" + rmsgvalue + '\'' +
                ", flag=" + flag +
                '}';
    }

    public void clear(){
        this.setKey("");
        this.setMsgvalue("");
        this.setRmsgvalue("");
        this.setFlag(4);
    }


}