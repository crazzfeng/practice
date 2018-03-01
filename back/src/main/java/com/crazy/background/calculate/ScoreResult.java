package com.crazy.background.calculate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 练习所用类，可删
 */
public class ScoreResult
{
    /**
     * 最大投标价
     */
    private Double m_max;
    /**
     * 最小投标价
     */
    private Double m_min;
    /**
     * 平均投标价
     */
    private Double m_avg;
    /**
     * 投标商个数
     */
    private Double m_account;
    /**
     * 投标总价
     */
    private Double m_sum;
    /**
     * 投标报价权重或投标报价得分基准分
     */
    private Double m_weight;
    /**
     * 投标控制价
     */
    private Double m_controlPrice;
    /**
     * 投标控制价
     */
    private Double m_bidderPrice;
    /**
     * 打分规则表达式
     */
    private String m_rule;
    
    public ScoreResult(String rule, Double max, Double min, Double avg, Double acc, Double sum, Double wig, Double ctl,Double bid)
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
        StringBuffer stringBuffer = new StringBuffer();
        List<String> rules = new ArrayList<String>();
        for (int i = 0; i < strings.length; i++)
        {
            if ("max".equals(rules.get(i)))
            {
                stringBuffer.append(m_max);
                rules.add(m_max.toString());
            }
            else if ("min".equals(rules.get(i)))
            {
                stringBuffer.append(m_min);
                rules.add(m_min.toString());
            }
            else if ("avg".equals(rules.get(i)))
            {
                stringBuffer.append(m_avg);
                rules.add(m_avg.toString());
            }
            else if ("acc".equals(rules.get(i)))
            {
                stringBuffer.append(m_account);
                rules.add(m_account.toString());
            }
            else if ("sum".equals(rules.get(i)))
            {
                stringBuffer.append(m_sum);
                rules.add(m_sum.toString());
            }
            else if ("wig".equals(rules.get(i)))
            {
                stringBuffer.append(m_weight);
                rules.add(m_weight.toString());
            }
            else if ("ctl".equals(rules.get(i)))
            {
                stringBuffer.append(m_controlPrice);
                rules.add(m_controlPrice.toString());
            }
            else if ("bid".equals(rules.get(i)))
            {
                stringBuffer.append(m_bidderPrice);
                rules.add(m_bidderPrice.toString());
            }
            else if (!"".equals(strings[i].trim()))
            {
                stringBuffer.append(strings[i]);
                rules.add(strings[i]);
            }
            else
            {
                continue;
            }

        }
        return rules;
    }



    private List<String> judge(List<String> simplerule)
    {
        for (int i = 0; i < simplerule.size(); i++)
        {
            if ("?".equals(simplerule.get(i)))
            {
                if (i == 0 || i == 1 || i == 2)
                {
                    throw new RuntimeException("打分规则表达式配置错误，'?'用法错误！");
                }

                if (">=".equals(simplerule.get(i - 2)))//大于等于
                {
                    simplerule = getjudgeres(simplerule, i, new BigDecimal(simplerule.get(i - 3)).compareTo(new BigDecimal(simplerule.get(i - 1))) >= 0);
                    break;
                }

                if ("==".equals(simplerule.get(i - 2)))//等于
                {
                    simplerule = getjudgeres(simplerule, i, new BigDecimal(simplerule.get(i - 3)).compareTo(new BigDecimal(simplerule.get(i - 1))) == 0);
                    break;

                }

                if ("<=".equals(simplerule.get(i - 2)))//小于等于
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
        if (res)//判断三目表达式
        {
            for (int j = i + 1; j < simplerule.size(); j++)
            {
                if ("?".equals(simplerule.get(j)))
                {
                    a++;
                }
                if (":".equals(simplerule.get(j)))
                {
                    if (a == 0)
                    {
                        a = j;
                        break;
                    }
                    a--;
                }
            }
            //得到判断后的结果
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
            for (int j = i + 1; j < simplerule.size(); j++)
            {
                if ("?".equals(simplerule.get(j)))
                {
                    a++;
                }
                if (":".equals(simplerule.get(j)))
                {
                    if (a == 0)
                    {
                        a = j;
                        break;
                    }
                    a--;
                }
            }
            //得到判断后的结果
            for (int k = 0; k < a + 1; k++)
            {
                simplerule.remove(0);
            }
            simplerule = judge(simplerule);
        }

        return simplerule;
    }
}
