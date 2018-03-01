package com.crazy.background.calculate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ScoreRule
{
    /**
     * 最大投标价
     */
    private BigDecimal m_max;
    /**
     * 最小投标价
     */
    private BigDecimal m_min;
    /**
     * 平均投标价
     */
    private BigDecimal m_avg;
    /**
     * 投标商个数
     */
    private BigDecimal m_account;
    /**
     * 投标总价
     */
    private BigDecimal m_sum;
    /**
     * 投标报价权重或投标报价得分基准分
     */
    private BigDecimal m_weight;
    /**
     * 投标控制价
     */
    private BigDecimal m_controlPrice;
    /**
     * 投标控制价
     */
    private BigDecimal m_bidderPrice;

    /**
     * 数学算法
     */
    private final String ADD = "+";
    private final String SUB = "-";
    private final String MUL = "*";
    private final String DIV = "/";
    private final String LEF = "(";
    private final String RIG = ")";

    /**
     * 逻辑算法
     */
    private final String EQL = "==";
    private final String GT = ">=";
    private final String LT = "<=";
    private final String JUD = "?";
    private final String COL = ":";

    private String m_rule;
   /* private List<BigDecimal> m_prices;*/

    public ScoreRule(String rule, BigDecimal max, BigDecimal min, BigDecimal avg, BigDecimal acc, BigDecimal sum, BigDecimal wig, BigDecimal ctl,BigDecimal bid)
    {
        m_rule = rule;
        m_max = max;
        m_min = min;
        m_avg = avg;
        m_account = acc;
        m_sum = sum;
        m_weight = wig;
        m_controlPrice = ctl;
        m_bidderPrice = bid;
    }

    //解析规则表达式
    private List<String> getRule()
    {
        if (null == m_rule || "".equals(m_rule.trim()))
        {
            return null;
        }
        String[] strings = m_rule.split(" ");
        List<String> rules = new ArrayList<String>();
        for (int i = 0; i < strings.length; i++)
        {
            if (!"".equals(strings[i].trim()))
            {
                rules.add(strings[i]);
            }
        }
        for (int i = 0; i < rules.size(); i++)
        {
            if ("max".equals(rules.get(i)))
            {
                rules.set(i, m_max.toString());
            }
            else if ("min".equals(rules.get(i)))
            {
                rules.set(i, m_min.toString());
            }
            else if ("avg".equals(rules.get(i)))
            {
                rules.set(i, m_avg.toString());
            }
            else if ("acc".equals(rules.get(i)))
            {
                rules.set(i, m_account.toString());
            }
            else if ("sum".equals(rules.get(i)))
            {
                rules.set(i, m_sum.toString());
            }
            else if ("wig".equals(rules.get(i)))
            {
                rules.set(i, m_weight.toString());
            }
            else if ("ctl".equals(rules.get(i)))
            {
                rules.set(i, m_controlPrice.toString());
            }
            else if ("bid".equals(rules.get(i)))
            {
                rules.set(i, m_bidderPrice.toString());
            }
            else
            {
                continue;
            }

        }
        return rules;
    }

    public BigDecimal getResult()
    {
        List<String> rule = getRule();
        if (rule == null)
        {
            return null;
        }
        return clearbrackets(rule);

    }

    /**
     * 带括号的去掉括号一步一步运算
     */
    private BigDecimal clearbrackets(List<String> strings)
    {
        boolean bracket = true;
        BigDecimal result = null;
        do
        {
            List<Integer> list1 = new ArrayList<Integer>();
            List<Integer> list2 = new ArrayList<Integer>();
            for (int i = 0; i < strings.size(); i++)
            {
                if (LEF.equals(strings.get(i)))
                {
                    list1.add(i);
                }
                else if (RIG.equals(strings.get(i)))
                {
                    list2.add(i);
                }
            }
            if (list1.size() != list2.size())
            {
                throw new RuntimeException("打分规则表达式配置错误，左右括号数量不等！");
            }
            if (list1 == null || list1.size() == 0) //不带括号
            {
                result = getvaluein(strings);
                bracket = false;
            }
            else //带括号
            {
                int a = 1;
                for (int i = 0; i < strings.size(); i++)
                {
                    if (RIG.equals(strings.get(i)))
                    {
                        a = i;
                        break;
                    }
                }
                for (int i = a; i >= 0; i--)
                {
                    if (LEF.equals(strings.get(i)))
                    {
                        List<String> stringList = strings.subList(i + 1, a);
                        BigDecimal res = getvaluein(stringList);
                        for (int j = i + 1; j < i + 3; j++)
                        {
                            strings.remove(i + 1);
                        }
                        strings.set(i, res.toString());
                /*Collections.replaceAll(strings,strings.get(i),res.toString());*/
                        break;
                    }
                }
            }

        }
        while (bracket);
        return result;
    }

    /**
     * 先乘除 后加减
     */
    private BigDecimal getvaluein(List<String> rule)
    {
        List<String> simplerule = rule;
        for (int i = 0; i < simplerule.size(); i++)
        {
            if (MUL.equals(simplerule.get(i)))
            {
                if (i == 0)
                {
                    throw new RuntimeException("打分规则表达式配置错误，开头不能是'*'！");
                }
                BigDecimal first = new BigDecimal(simplerule.get(i - 1));
                BigDecimal second = new BigDecimal(simplerule.get(i + 1));
                BigDecimal result = first.multiply(second);
               /* Collections.replaceAll(simplerule, simplerule.get(i - 1), result.toString());*/
                simplerule.set(i - 1, result.toString());
                simplerule.remove(i);
                simplerule.remove(i);
                i = i - 2;
                continue;
            }
            if (DIV.equals(simplerule.get(i)))
            {
                if (i == 0)
                {
                    throw new RuntimeException("打分规则表达式配置错误，开头不能是'/'！");
                }
                BigDecimal first = new BigDecimal(simplerule.get(i - 1));
                BigDecimal second = new BigDecimal(simplerule.get(i + 1));
                BigDecimal result = first.divide(second,4,BigDecimal.ROUND_HALF_DOWN);
                /*Collections.replaceAll(simplerule, simplerule.get(i - 1), result.toString());*/
                simplerule.set(i - 1, result.toString());
                simplerule.remove(i);
                simplerule.remove(i);
                i = i - 2;
                continue;
            }
        }
        for (int i = 0; i < simplerule.size(); i++)
        {
            if (ADD.equals(simplerule.get(i)))
            {
                if (i == 0)
                {
                    throw new RuntimeException("打分规则表达式配置错误，开头不能是'+'！");
                }
                BigDecimal first = new BigDecimal(simplerule.get(i - 1));
                BigDecimal second = new BigDecimal(simplerule.get(i + 1));
                BigDecimal result = first.add(second);
               /* Collections.replaceAll(simplerule, simplerule.get(i - 1), result.toString());*/
                String ref = simplerule.set(i - 1, result.toString());
                simplerule.remove(i);
                simplerule.remove(i);
                i = i - 2;
                continue;
            }
            if (SUB.equals(simplerule.get(i)))
            {
                if (i == 0)
                {
                    throw new RuntimeException("打分规则表达式配置错误，开头不能是'-'！");
                }
                BigDecimal first = new BigDecimal(simplerule.get(i - 1));
                BigDecimal second = new BigDecimal(simplerule.get(i + 1));
                BigDecimal result = first.subtract(second);
                /*Collections.replaceAll(simplerule, simplerule.get(i - 1), result.toString());*/
                simplerule.set(i - 1, result.toString());
                simplerule.remove(i);
                simplerule.remove(i);
                i = i - 2;
                continue;
            }
        }

        simplerule = judge(simplerule);

        if (simplerule.size() != 1)
        {
            throw new RuntimeException("表达式计算有误！");
        }
        else
        {
            return new BigDecimal(simplerule.get(0));
        }
    }

    /**
     * 判断三目表达式
     */
    private List<String> judge(List<String> simplerule)
    {
        for (int i = 0; i < simplerule.size(); i++)
        {
            if (JUD.equals(simplerule.get(i)))
            {
                if (i == 0 || i == 1 || i == 2)
                {
                    throw new RuntimeException("打分规则表达式配置错误，'?'用法错误！");
                }

                if (GT.equals(simplerule.get(i - 2)))//大于等于
                {
                    simplerule = getjudgeres(simplerule, i, new BigDecimal(simplerule.get(i - 3)).compareTo(new BigDecimal(simplerule.get(i - 1))) >= 0);
                    break;
                }

                if (EQL.equals(simplerule.get(i - 2)))//等于
                {
                    simplerule = getjudgeres(simplerule, i, new BigDecimal(simplerule.get(i - 3)).compareTo(new BigDecimal(simplerule.get(i - 1))) == 0);
                    break;
                }

                if (LT.equals(simplerule.get(i - 2)))//小于等于
                {
                    simplerule = getjudgeres(simplerule, i, new BigDecimal(simplerule.get(i - 3)).compareTo(new BigDecimal(simplerule.get(i - 1))) <= 0);
                    break;
                }
            }
        }
        return simplerule;
    }

    //得到三目表达式判断后的结果
    private List<String> getjudgeres(List<String> simplerule, int i, boolean res)
    {
        int a = 0;
        //找到当前三目表达式的两种结果分割点
        for (int j = i + 1; j < simplerule.size(); j++)
        {
            if (JUD.equals(simplerule.get(j)))
            {
                a++;
            }
            if (COL.equals(simplerule.get(j)))
            {
                if (a == 0)
                {
                    a = j;
                    break;
                }
                a--;
            }
        }

        //判断三目表达式，得到判断后的结果
        if (res)
        {
            int size = simplerule.size();
            for (int k = a; k < size; k++)
            {
                simplerule.remove(a);
            }
            for (int k = 0; k < i + 1; k++)
            {
                simplerule.remove(0);
            }
            simplerule = judge(simplerule);
        }
        else
        {
            for (int k = 0; k < a + 1; k++)
            {
                simplerule.remove(0);
            }
            simplerule = judge(simplerule);
        }
        return simplerule;
    }
}
