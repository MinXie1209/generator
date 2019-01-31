     package top.myjnxj.generator.common.exception;

     import top.myjnxj.generator.common.enums.ResultEnum;

     /**
      * @author 江神
      * @create ${now}
      * @desc 自定义异常
      **/
     public class ResultException extends Exception {
         private Integer code;

         public ResultException(ResultEnum error) {
             super(error.getMsg());
             this.code = error.getCode();
         }

         public Integer getCode() {
             return code;
         }
     }
