package com.crazy.background.calculate;

import java.math.BigDecimal;

public class TestRule
{
    public static void main(String[] args)
    {
        Integer[] ar = new Integer[]{1,2,3,4,5,6,7,8,9};

        //评分规则：工程招标：总分50分 平均价作为基数如果正负1%以内是满分 高1%加1分 低1%扣0.5分,下限40分；
        String ruleEngin = "bid - avg >= 0 ? ( bid - avg ) / avg / 0.01 + 50 : ( bid - avg ) / avg / 0.01 * 0.5 + 50 ";
        //设备服务招标：把最低价作为基本参考价，为40分.
        String ruleService = "min / bid * 40 ";
        String rule = "( max + min ) * acc / 2 == sum ? sum : ( max + min == 10 ? avg * acc : sum )";
        BigDecimal max = new BigDecimal(9);
        BigDecimal min = new BigDecimal(1);
        BigDecimal avg = new BigDecimal(5);
        BigDecimal acc = new BigDecimal(9);
        BigDecimal sum = new BigDecimal(45);
        BigDecimal wig = new BigDecimal(0.5);
        BigDecimal ctl = new BigDecimal(3);
        BigDecimal bid = new BigDecimal(3);
        ScoreRule scoreRuleEngin = new ScoreRule(ruleEngin,max,min,avg,acc,sum,wig,ctl,bid);
        ScoreRule scoreRuleService = new ScoreRule(ruleService,max,min,avg,acc,sum,wig,ctl,bid);
        ScoreRule scoreRule = new ScoreRule(rule,max,min,avg,acc,sum,wig,ctl,bid);

        BigDecimal result =  scoreRule.getResult();
        BigDecimal result1 =  scoreRuleEngin.getResult();
        BigDecimal result2 =  scoreRuleService.getResult();
        System.err.print(result.doubleValue() + "\n");
        System.err.print(result1.doubleValue() + "\n");
        System.err.print(result2.doubleValue() + "\n");



       /* double re = Calculator.conversion("(3-5)/5/0.01");
        System.err.print(re);*/
    }

}
