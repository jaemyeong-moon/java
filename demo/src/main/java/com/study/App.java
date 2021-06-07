package com.study;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * Hello world!
 *
 */
interface Compare
{
    public int compareTo(int value1, int value2);
}
public class App 
{
    public static void exec(Compare compare)
    {
        int k = 10;
        int m = 20;
        int value = compare.compareTo(k, m);
        System.out.println(value);
    }
    public static void main( String[] args )
    {
        /*
            익명 메소드. 
            람다식을 사용하는 주된 이유중 하나로, New로 객체를 생성할 필요 없이, (메소드의 파라메타...)->{ 구현 내용 } 을 통해서 
            객체를 생성한다. 
            기존 객체를 생성 및 인터페이스에 대해 구현하는 내용이, 기존보다 단순화된다. 
            인터페이스가 여러개의 메소드를 갖는경우는 
            https://stackoverflow.com/questions/21833537/java-8-lambda-expressions-what-about-multiple-methods-in-nested-class 해당글을 참조한다. 
            (마우스 이벤트를 헬퍼인터페이스를 통해 받음 )

            메소드가 동작없이 리턴값만 있는경우
            exec( (i,j) -> i-j); 중괄호와 return 을 생략하여 작성할수 있음. 

        */
        exec( (i,j) -> {
            return i-j;
        });

        /*
            스트림
            컬렉션 인스턴스에 함수 여러개를 조합해서 필터링 후 원하는 가공된 결과를 얻을 수 있다.
            for/foreach 등 반복문을 사용하는 경우보다 코드를 훨씬 간결하게 만들 수 있다는 장점을 갖고 있다. 
            쓰레드를 이용해 병렬처리가 가능하다. 
        */

        //배열 스트림
        String[] arr = new String[]{"a", "b", "c"};
        Stream<String> stream = Arrays.stream(arr);
        Stream<String> streamOfArray = Arrays.stream(arr, 1, 3); // 배열의 일부 원소만 스트림으로 만들 때

        List<String> list = Arrays.asList("a", "b", "c");
        Stream<String> stream2 = list.stream();
        Stream<String> parallelStream = list.parallelStream();
        Stream<String> builderStream = Stream.<String>builder().add("Eric").add("Elena").add("Java").build();
        Stream<String> generateStream = Stream.generate(() -> "gen").limit(5);
        Stream<Integer> iterateStream = Stream.iterate(10, n -> 10 + 5).limit(5); // 10, 15, 20, 25, 30
        
    }
}
