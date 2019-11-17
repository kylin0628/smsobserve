package com.android.smsutil.utils;

import android.util.Log;

import com.android.smsutil.StringUtils;
import com.android.smsutil.bean.BankRegluar;

public class RegularUtil {
    public static BankRegluar getBankRegEntity(String body) {


        String amount = "error";
        String bankNo = "error";

        if (body.contains("*")) {
            body = body.replace("*", "");
        }
        if (body != null && !body.equals("")) {
            Log.e("string", "body------->" + body);
            if (body.contains("银联入账")) {
                if (body.contains("中国农业银行")) {
                    if (amount.equals("error") || bankNo.equals("error") || amount.equals("") || bankNo.equals("")) {
                        amount = StringUtils.getTextCenter(body, "银联入账交易人民币", "，");
                        bankNo = StringUtils.getTextCenter(body, "尾号", "账户");
                    }
                } else if (body.contains("建设银行")) {
                    if (amount.equals("error") || bankNo.equals("error") || amount.equals("") || bankNo.equals("")) {
                        amount = StringUtils.getTextCenter(body, "银联入账收入人民币", "元");
                        bankNo = StringUtils.getTextCenter(body, "尾号", "的储蓄卡");
                    }
                } else if (body.contains("95511-3")) {
                    if (amount.equals("error") || bankNo.equals("error") || amount.equals("") || bankNo.equals("")) {
                        amount = StringUtils.getTextCenter(body, "银联入账转入人民币", "元");
                        bankNo = StringUtils.getTextCenter(body, "尾号", "的账户");
                    }
                } else if (body.contains("民生银行")) {
                    if (amount.equals("error") || bankNo.equals("error") || amount.equals("") || bankNo.equals("")) {
                        amount = StringUtils.getTextCenter(body, "存入￥", "元");
                        bankNo = StringUtils.getTextCenter(body, "账户", "于");
                    }
                } else if (body.contains("浦发银行")) {
                    if (amount.equals("error") || bankNo.equals("error") || amount.equals("") || bankNo.equals("")) {
                        amount = StringUtils.getTextCenter(body, "存入", "[银联");
                        bankNo = StringUtils.getTextCenter(body, "您尾号", "卡人民币");
                    }
                } else if (body.contains("招商银行")) {
                    if (amount.equals("error") || bankNo.equals("error") || amount.equals("") || bankNo.equals("")) {
                        amount = StringUtils.getTextCenter(body, "银联入账人民币", "元");
                        bankNo = StringUtils.getTextCenter(body, "您账户", "于");
                    }
                } else if (body.contains("支付宝")) {
                    if (amount.equals("error") || bankNo.equals("error") || amount.equals("") || bankNo.equals("")) {
                        amount = StringUtils.getTextCenter(body, "存入", "元");
                        bankNo = StringUtils.getTextCenter(body, "尾号", "账户");
                    }
                } else if (body.contains("邮储银行")) {
                    if (amount.equals("error") || bankNo.equals("error") || amount.equals("") || bankNo.equals("")) {
                        amount = StringUtils.getTextCenter(body, "银联入账金额", "元");
                        amount = amount.replace(",", "");
                        bankNo = StringUtils.getTextCenter(body, "尾号", "账户");
                    }
                } else if (body.contains("广州农商银行")) {
                    if (amount.equals("error") || bankNo.equals("error") || amount.equals("") || bankNo.equals("")) {
                        amount = StringUtils.getTextCenter(body, "银联入账收入人民币", "元");
                        amount = amount.replace(",", "");
                        bankNo = StringUtils.getTextCenter(body, "尾号", "的");
                    }
                }else if (body.contains("福建农信")) {
                    if (amount.equals("error") || bankNo.equals("error") || amount.equals("") || bankNo.equals("")) {
                        amount = StringUtils.getTextCenter(body, "收入", "人民币");
                        bankNo = StringUtils.getTextCenter(body, "您的", "账户");
                    }
                }else if (body.contains("工商银行")) {
                    if (amount.equals("error") || bankNo.equals("error") || amount.equals("") || bankNo.equals("")) {
                        amount = StringUtils.getTextCenter(body, ")", "元");
                        bankNo = StringUtils.getTextCenter(body, "您尾号", "卡");
                    }
                }else if (body.contains("广发银行")) {
                    if (amount.equals("error") || bankNo.equals("error") || amount.equals("") || bankNo.equals("")) {
                        amount = StringUtils.getTextCenter(body, "人民币", "元");
                        bankNo = StringUtils.getTextCenter(body, "您尾号", "卡");
                    }
                }
            } else if (body.contains("网联付款收入")) {
                if (body.contains("兴业银行")) {
                    if (amount.equals("error") || bankNo.equals("error") || amount.equals("") || bankNo.equals("")) {
                        amount = StringUtils.getTextCenter(body, "网联付款收入", "元");
                        bankNo = StringUtils.getTextCenter(body, "账户", "网联");
                    }
                }
            }  else if (body.contains("网联入账")) {
                if (body.contains("海峡银行")) {
                    if (amount.equals("error") || bankNo.equals("error") || amount.equals("") || bankNo.equals("")) {
                        amount = StringUtils.getTextCenter(body, "存入", "元");
                        bankNo = StringUtils.getTextCenter(body, "尾号为", "的卡");
                    }
                }
            } else if (body.contains("跨行代付收入")) {
                if (body.contains("兴业银行")) {
                    if (amount.equals("error") || bankNo.equals("error") || amount.equals("") || bankNo.equals("")) {
                        amount = StringUtils.getTextCenter(body, "跨行代付收入", "元");
                        bankNo = StringUtils.getTextCenter(body, "账户", "跨行");
                    }
                }
            } else if (body.contains("快捷支付收入")) {
                if (body.contains("工商银行")) {
                    if (amount.equals("error") || bankNo.equals("error") || amount.equals("") || bankNo.equals("")) {
                        amount = StringUtils.getTextCenter(body, ")", "元");
                        amount = amount.replace(",", "");
                        bankNo = StringUtils.getTextCenter(body, "尾号", "卡");
                    }
                }
            } else if (body.contains("网上银行收入")) {
                if (body.contains("工商银行")) {
                    if(body.contains("支付宝")||body.contains("银行卡"))
                    {
                        if (amount.equals("error") || bankNo.equals("error") || amount.equals("") || bankNo.equals("")) {
                            amount = StringUtils.getTextCenter(body, ")", "元");
                            amount = amount.replace(",", "");
                            bankNo = StringUtils.getTextCenter(body, "尾号", "卡");
                        }
                    }
                    else
                    {
                        if (amount.equals("error") || bankNo.equals("error") || amount.equals("") || bankNo.equals("")) {
                            amount = StringUtils.getTextCenter(body, "）", "，");
                            amount = amount.replace(",", "");
                            bankNo = StringUtils.getTextCenter(body, "尾号", "卡");
                        }
                    }

                }
            } else if (body.contains("网络支付转入")) {
                if (body.contains("交通银行")) {
                    if (amount.equals("error") || bankNo.equals("error") || amount.equals("") || bankNo.equals("")) {
                        amount = StringUtils.getTextCenter(body, "网络支付转入", "元");
                        amount = amount.replace(",", "");
                        bankNo = StringUtils.getTextCenter(body, "尾号", "的卡");
                    }
                }
            } else if (body.contains("二维码商户入账")) {
                if (body.contains("贵州农信")) {
                    if (amount.equals("error") || bankNo.equals("error") || amount.equals("") || bankNo.equals("")) {
                        amount = StringUtils.getTextCenter(body, "二维码商户入账收入", "元");
                        amount = amount.replace(",", "");
                        bankNo = StringUtils.getTextCenter(body, "尾号", "卡");
                    }
                }
            } else if (body.contains("您注册的商户收到")) {
                if (body.contains("支付宝")) {
                    if (amount.equals("error") || bankNo.equals("error") || amount.equals("") || bankNo.equals("")) {
                        amount = StringUtils.getTextCenter(body, "您注册的商户收到", "元");
                        amount = amount.replace(",", "");
                        bankNo = StringUtils.getTextCenter(body, "客户ID", "。");
                    }
                } else if (body.contains("微信")) {
                    if (amount.equals("error") || bankNo.equals("error") || amount.equals("") || bankNo.equals("")) {
                        amount = StringUtils.getTextCenter(body, "您注册的商户收到", "元");
                        amount = amount.replace(",", "");
                        bankNo = amount;
                    }
                }
            } else {
                if (body.contains("交通银行")) {
                    if (body.contains("支付宝")&&body.contains("的卡")) {
                        if (amount.equals("error") || bankNo.equals("error") || amount.equals("") || bankNo.equals("")) {
                            amount = StringUtils.getTextCenter(body, "网银转入", "元");
                            bankNo = StringUtils.getTextCenter(body, "尾号", "的");
                        }
                    }else if(body.contains("财付通")&&body.contains("的卡")){
                        if (amount.equals("error") || bankNo.equals("error") || amount.equals("") || bankNo.equals("")) {
                            amount = StringUtils.getTextCenter(body, "网银转入", "元");
                            bankNo = StringUtils.getTextCenter(body, "尾号", "的");
                        }
                    } else {
                        if (amount.equals("error") || bankNo.equals("error") || amount.equals("") || bankNo.equals("")) {
                            amount = StringUtils.getTextCenter(body, "网银转入", "元");
                            bankNo = StringUtils.getTextCenter(body, "尾号", "于");
                        }
                    }
                } else if (body.contains("光大银行")) {
                    if (body.contains("支付宝")) {
                        if (amount.equals("error") || bankNo.equals("error") || amount.equals("") || bankNo.equals("")) {
                            amount = StringUtils.getTextCenter(body, "存入", "元");
                            bankNo = StringUtils.getTextCenter(body, "尾号", "账户");
                        }
                    }else if (body.contains("转账到银行卡")){
                        if (amount.equals("error") || bankNo.equals("error") || amount.equals("") || bankNo.equals("")) {
                            amount = StringUtils.getTextCenter(body, "存入", "元");
                            bankNo = StringUtils.getTextCenter(body, "尾号", "账户");
                        }
                    }
                } else if (body.contains("邮储银行")) {
                    if (body.contains("提现")) {
                        if (amount.equals("error") || bankNo.equals("error") || amount.equals("") || bankNo.equals("")) {
                            amount = StringUtils.getTextCenter(body, "提现金额", "元");
                            bankNo = StringUtils.getTextCenter(body, "尾号", "账户");
                        }
                    } else {
                        if (amount.equals("error") || bankNo.equals("error") || amount.equals("") || bankNo.equals("")) {
                            amount = StringUtils.getTextCenter(body, "入账金额", "元");
                            amount = amount.replace(",", "");
                            bankNo = StringUtils.getTextCenter(body, "尾号", "账户");
                        }
                    }

                } else if (body.contains("工商银行")) {
                    if (amount.equals("error") || bankNo.equals("error") || amount.equals("") || bankNo.equals("")) {
                        amount = StringUtils.getTextCenter(body, "支付宝转账)", "元");
                        amount = amount.replace(",", "");
                        bankNo = StringUtils.getTextCenter(body, "尾号", "卡");
                    }
                } else if (body.contains("兴业银行")) {
                    if (body.contains("网联付款")) {
                        if (amount.equals("error") || bankNo.equals("error") || amount.equals("") || bankNo.equals("")) {
                            amount = StringUtils.getTextCenter(body, "网联付款收入", "元");
                            bankNo = StringUtils.getTextCenter(body, "账户", "网联");
                        }
                    } else {
                        if (amount.equals("error") || bankNo.equals("error") || amount.equals("") || bankNo.equals("")) {
                            amount = StringUtils.getTextCenter(body, "收入", "元");
                            amount = amount.replace(",", "");
                            bankNo = StringUtils.getTextCenter(body, "账户", "收入");
                        }
                    }

                } else if (body.contains("中原银行")) {
                    if (amount.equals("error") || bankNo.equals("error") || amount.equals("") || bankNo.equals("")) {
                        amount = StringUtils.getTextCenter(body, "转入", "元");
                        bankNo = StringUtils.getTextCenter(body, "尾号", "的卡");
                    }
                } else if (body.contains("河南农信")) {
                    if (amount.equals("error") || bankNo.equals("error") || amount.equals("") || bankNo.equals("")) {
                        amount = StringUtils.getTextCenter(body, "转入", "元");
                        bankNo = StringUtils.getTextCenter(body, "尾号", "于");
                    }
                } else if (body.contains("江西农信")) {
                    if (amount.equals("error") || bankNo.equals("error") || amount.equals("") || bankNo.equals("")) {
                        amount = StringUtils.getTextCenter(body, "人民币", "元");
                        bankNo = StringUtils.getTextCenter(body, "尾号", "的账户");
                    }
                } else if (body.contains("中国银行")) {
                    if (amount.equals("error") || bankNo.equals("error") || amount.equals("") || bankNo.equals("")) {
                        amount = StringUtils.getTextCenter(body, "收入人民币", "元");
                        amount = amount.replace(",", "");
                        bankNo = StringUtils.getTextCenter(body, "借记卡账户", "，");
                    }
                } else if (body.contains("郑州银行")) {
                    if (amount.equals("error") || bankNo.equals("error") || amount.equals("") || bankNo.equals("")) {
                        amount = StringUtils.getTextCenter(body, "收款人民币", "元");
                        amount = amount.replace(",", "");
                        bankNo = StringUtils.getTextCenter(body, "尾号为", "的卡");
                    }
                } else if (body.contains("华夏银行")) {
                    if (amount.equals("error") || bankNo.equals("error") || amount.equals("") || bankNo.equals("")) {
                        amount = StringUtils.getTextCenter(body, "人民币", "元");
                        amount = amount.replace(",", "");
                        bankNo = StringUtils.getTextCenter(body, "账户", "于");
                    }
                } else if (body.contains("湖南农信")) {
                    if (amount.equals("error") || bankNo.equals("error") || amount.equals("") || bankNo.equals("")) {
                        amount = StringUtils.getTextCenter(body, "人民币", "元");
                        amount = amount.replace(",", "");
                        bankNo = StringUtils.getTextCenter(body, "尾号", "的卡");
                    }
                } else if (body.contains("建设银行")) {
                    if (amount.equals("error") || bankNo.equals("error") || amount.equals("") || bankNo.equals("")) {
                        amount = StringUtils.getTextCenter(body, "收入人民币", "元");
                        bankNo = StringUtils.getTextCenter(body, "尾号", "的储蓄卡");
                    }
                } else if (body.contains("福建农信")) {
                    if (amount.equals("error") || bankNo.equals("error") || amount.equals("") || bankNo.equals("")) {
                        amount = StringUtils.getTextCenter(body, "收入", "人民币");
                        bankNo = StringUtils.getTextCenter(body, "您的", "账户");
                    }
                } else if (body.contains("招商银行")) {
                    if(body.contains("财付通"))
                    {
                        if (amount.equals("error") || bankNo.equals("error") || amount.equals("") || bankNo.equals("")) {
                            amount = StringUtils.getTextCenter(body, "人民币", "，");
                            bankNo = StringUtils.getTextCenter(body, "您账户", "于");
                        }
                    }
                    else if(body.contains("支付宝-支付宝"))
                    {
                        if (amount.equals("error") || bankNo.equals("error") || amount.equals("") || bankNo.equals("")) {
                            amount = StringUtils.getTextCenter(body, "人民币", "，");
                            bankNo = StringUtils.getTextCenter(body, "您账户", "于");
                        }
                    }else{
                        if (amount.equals("error") || bankNo.equals("error") || amount.equals("") || bankNo.equals("")) {
                            amount = StringUtils.getTextCenter(body, "人民币", "元");
                            bankNo = StringUtils.getTextCenter(body, "您账户", "于");
                        }
                    }
                } else {
                    if (body.contains("您注册的商户收到客户付款")) {
                        if (amount.equals("error") || bankNo.equals("error") || amount.equals("") || bankNo.equals("")) {
                            amount = StringUtils.getTextCenter(body, "您注册的商户收到客户付款", "元");
                            bankNo = amount;
                        }
                    } else {
                        amount = StringUtils.getTextCenter(body, "人民币", "元");
                        bankNo = StringUtils.getTextCenter(body, "尾号", "的");
                        if (amount.equals("error") || bankNo.equals("error") || amount.equals("") || bankNo.equals("")) {
                            amount = StringUtils.getTextCenter(body, "存入￥", "元");
                            bankNo = StringUtils.getTextCenter(body, "账户", "于");
                        }

                        if (amount.equals("error") || bankNo.equals("error") || amount.equals("") || bankNo.equals("")) {
                            amount = StringUtils.getTextCenter(body, ")", "元");
                            bankNo = StringUtils.getTextCenter(body, "尾号", "卡");
                        }
                        if (amount.equals("error") || bankNo.equals("error") || amount.equals("") || bankNo.equals("")) {
                            amount = StringUtils.getTextCenter(body, "存入", "[");
                            bankNo = StringUtils.getTextCenter(body, "尾号", "卡");
                        }
                        if (amount.equals("error") || bankNo.equals("error") || amount.equals("") || bankNo.equals("")) {
                            amount = StringUtils.getTextCenter(body, "收入", "元");
                            bankNo = StringUtils.getTextCenter(body, "尾号为", "的");
                        }

                        if (amount.equals("error") || bankNo.equals("error") || amount.equals("") || bankNo.equals("")) {
                            amount = StringUtils.getTextCenter(body, "人民币", "，");
                            bankNo = StringUtils.getTextCenter(body, "尾号", "账户");
                        }

                        if (amount.equals("error") || bankNo.equals("error") || amount.equals("") || bankNo.equals("")) {
                            amount = StringUtils.getTextCenter(body, "人民币", "，");
                            bankNo = StringUtils.getTextCenter(body, "账户", "于");
                        }
                        if (amount.equals("error") || bankNo.equals("error") || amount.equals("") || bankNo.equals("")) {
                            amount = StringUtils.getTextCenter(body, "人民币", "元");
                            bankNo = StringUtils.getTextCenter(body, "尾号", "的");
                        }
                    }

                }
            }
        }
        return new BankRegluar(amount,bankNo,body);
    }

}
