package ba.fit.srednjeskole.helper;

public class MyApiResult {
    public String errorMessage = "";
    public boolean isException = false;
    public int resultcode = 0;
    public String value;

    public MyApiResult() {
    }

    public static MyApiResult Error(int exceptionCode, String exceptionMessage){
        MyApiResult x = new MyApiResult();
        x.isException = true;
        x.resultcode = exceptionCode;
        x.errorMessage = exceptionMessage;

        return x;
    }

    public static MyApiResult OK(String value){
        MyApiResult x = new MyApiResult();
        x.isException = false;
        x.resultcode = 200;
        x.value = value;

        return x;
    }





}
