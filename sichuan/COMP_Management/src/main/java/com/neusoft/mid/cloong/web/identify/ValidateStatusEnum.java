package com.neusoft.mid.cloong.web.identify;

import com.neusoft.mid.iamp.logger.StatusCode;

public enum ValidateStatusEnum implements StatusCode{

   SESSIONISNULL_EXCEPTION_CODE("1003"),
   AUTHENTICATERISNULL_EXCEPTION_CODE("1004");
   private final String code;

   ValidateStatusEnum(String code) {
       this.code = code;
   }

   @Override
   public String toString() {
       return code;
   }
}
