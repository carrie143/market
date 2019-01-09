package com.gop.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;


public class ExecuteTimeParser
{

    public static void main(String[] args)
        throws Exception
    {
        Pattern timePattern = Pattern.compile(".* \\[(.*?)\\] executeTime : (\\d+)ms");
        List<String> list = FileUtils.readLines(new File("d:/executeTime.txt"));
        Map<String, Integer> timeMaxMap = new HashMap<String, Integer>();
        Map<String, Integer> timeMinMap = new HashMap<String, Integer>();
        Map<String, List<Integer>> timeMap = new HashMap<String, List<Integer>>();
        Map<String, Double> timeAvgMap = new HashMap<String, Double>();
        for (String time : list)
        {
            Matcher matcher = timePattern.matcher(time);
            matcher.find();
            String method = matcher.group(1);
            Integer timing = Integer.valueOf(matcher.group(2));
            if (!timeMaxMap.containsKey(method))
            {
                timeMaxMap.put(method, 0);
            }
            timeMaxMap.put(method, Math.max(timeMaxMap.get(method), timing));
            if (!timeMinMap.containsKey(method))
            {
                timeMinMap.put(method, 0);
            }
            timeMinMap.put(method, Math.min(timeMinMap.get(method), timing));
            if (!timeMap.containsKey(method))
            {
                timeMap.put(method, new ArrayList<Integer>());
            }
            timeMap.get(method).add(timing);
        }
        // System.out.println(JSON.toJSONString(timeMaxMap, true));
        // System.out.println(JSON.toJSONString(timeMinMap, true));

        for (String method : timeMap.keySet())
        {

            timeAvgMap.put(method, calculateAverage(timeMap.get(method)));
        }

        System.out.println(String.format("\"method\",\"max\",\"min\",\"avg\""));
        for (String method : timeMaxMap.keySet())
        {
            System.out.println(String.format("\"%s\",\"%d\",\"%d\",\"%f\"", method,
                timeMaxMap.get(method), timeMinMap.get(method), timeAvgMap.get(method)));
        }

    }

    private static double calculateAverage(List<Integer> marks)
    {
        Integer sum = 0;
        if (!marks.isEmpty())
        {
            for (Integer mark : marks)
            {
                sum += mark;
            }
            return sum.doubleValue() / marks.size();
        }
        return sum;
    }

}
