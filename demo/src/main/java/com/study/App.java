package com.study;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * Hello world!
 *
 */
interface Compare
{
    public int compareTo(int value1, int value2);
}
class Product
{
    int id;
    int amount;
    Product(int id, int amount)
    {
        this.id = id;
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }
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

            https://futurecreator.github.io/2018/08/26/java-8-streams/
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
        IntStream intStream = IntStream.range(1,5); // {1, 2, 3, 4}
        LongStream longStream = LongStream.rangeClosed(1,5); // {1, 2, 3, 4,5}

        Stream<Integer> boxedIntStream = IntStream.range(1, 5).boxed(); // Stream<Integer> 로 캐스팅

        DoubleStream doubles = new Random().doubles(3); // 난수 3개 생성 
        
        IntStream charsStream = "Stream".chars(); //Stream이 바이트로 각각 들어감. 
        
        Stream<String> stringStream = Pattern.compile(", ").splitAsStream("Eric, Elena, Java");  // 정규식 표현을 통해 문자열을 잘라 스트림으로 만듬 


        try{
            Stream<String> lineStream = Files.lines(Paths.get("file.txt"), Charset.forName("UTF-8"));  //파일을 읽어서 라인들을 스트림으로 만듬 
            for(Object s : lineStream.toArray())
            {
                // System.out.println((String)s);
            }
        }catch(IOException e){}

        /*
        병렬 스트림 Parallel Stream
        스트림 생성 시 사용하는 stream 대신 parallelStream 메소드를 사용해서 병렬 스트림을 쉽게 
        생성할 수 있습니다. 내부적으로는 쓰레드를 처리하기 위해 자바 7부터 도입된 Fork/Join framework를
        사용합니다.        

        * 병렬 스트림 : 병렬 스트림은 데이터 병렬성을 구현한 것이다.
                    멀티코어의 수만큼 대용량 요소를 서브 요소들로 나누고, 각각의 서브 요소들을 분리된 스레드에서 병렬 처리시킨다.
                    예를 들어 쿼드 코어(Quad Core) CPU일 경우 4개의 서브 요소들로 나누고, 4개의 스레드가 각각의 서브 요소들을 병렬 처리한다.
                    병렬 스트림은 내부적으로 포크조인 프레임워크를 이용한다.
        출처 및 추가 정보 : https://ict-nroo.tistory.com/43
        */
        List<Product> productList = Arrays.asList(new Product(1,10), new Product(2,100), new Product(3,200), new Product(4,300) );
        Stream<Product> parallelStream2 = productList.parallelStream();

        boolean isParallel = parallelStream2.isParallel();
        System.out.println("isParallel : "+isParallel);

        boolean isMany = parallelStream2.map(product -> product.getAmount() ).anyMatch(amount -> amount > 200); //200개이상인 애들을 스트림에서 찾아서 있으면 True 반환.
        System.out.println("isMany : "+isMany);

        Arrays.stream(arr).parallel(); //Array -> Parallel Stream
        IntStream intStream2 = IntStream.range(1, 150).parallel(); 
        System.out.println("IsParallel ? "+intStream2.isParallel());
        IntStream intStream3 = intStream2.sequential(); // Parallel -> sequential
        System.out.println("IsParallel ? "+intStream2.isParallel());



        Stream<String> stream3 = Stream.of("Java", "Scala", "Groovy");
        Stream<String> stream4 = Stream.of("Python", "Go", "Swift");
        Stream<String> concat = Stream.concat(stream3, stream4); //스트림 연결.
        // [Java, Scala, Groovy, Python, Go, Swift]


        Example exam = new Example();
        exam.filtering();
        exam.mapping();
        exam.sorting();

    }
  
   
}
class Student
{
    int kor;
    int eng;
    int math;
    Student(int kor, int eng, int math)
    {
        setKor(kor);
        setEng(eng);
        setMath(math);
    }
    public int getKor() {
    	return this.kor;
    }
    public void setKor(int kor) {
    	this.kor = kor;
    }


    public int getEng() {
    	return this.eng;
    }
    public void setEng(int eng) {
    	this.eng = eng;
    }


    public int getMath() {
    	return this.math;
    }
    public void setMath(int math) {
    	this.math = math;
    }
}
class Example
{
    /*
    필터(filter)은 스트림 내 요소들을 하나씩 평가해서 걸러내는 작업입니다. 인자로 받는 Predicate 는 boolean 을 
    리턴하는 함수형 인터페이스로 평가식이 들어가게 됩니다.
    
    */
      /*스트림 가공하기*/
    void filtering()
    {
        List<String> names = Arrays.asList("Moon", "Jae", "Myeong");
        Stream<String> stream = names.stream().filter(name -> name.contains("M")); // [Moon,Myeong]
        for(Object s : stream.toArray())
        {
            System.out.println((String)s);
        }
    }
    void mapping()
    {
        List<String> names = Arrays.asList("Moon", "Jae", "Myeong");
        Stream<String> stream = names.stream().map(String::toUpperCase); //MOON,JAE,MYEONG
        // for(Object s : stream.toArray())
        // {
        //     System.out.println((String)s);
        // }
        List<Product> productList = Arrays.asList(new Product(1,10), new Product(2,100), new Product(3,200), new Product(4,300) );
        Stream<Integer> stream2 = productList.stream().map(Product::getAmount); // [10 100 200 300]
        // for(Object s : stream2.toArray())
        // {
        //     System.out.print((int)s+" ");
        // }
        List<Student> students = Arrays.asList(new Student(5,5,5), new Student(10,5,7), new Student(6,7,8));
        students.stream().flatMapToInt(student -> IntStream.of(student.getKor(), student.getEng(), student.getMath()))
        // .average().ifPresent(avg -> System.out.println(Math.round(avg *10)/10.0));
        .average().ifPresent(avg -> System.out.println(avg));
    }
    void sorting()
    {
        List<Object> list = IntStream.of(5,1,2,4,9,8).sorted().boxed().collect(Collectors.toList()); // 1 2 4 5 8 9
        for(Object o : list)
        {
            System.out.print((int)o+" ");
        }

        List<String> lang = Arrays.asList("Java", "Scala", "Groovy", "Python", "Go", "Swift");

        lang.stream().sorted().collect(Collectors.toList());
        // [Go, Groovy, Java, Python, Scala, Swift]

        /* Comaparator 연산자 + sorted() 조합으로  다양한 연산을 쉽게 표현할 수 있다. */
        lang.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList()); //Comparator 비교자 ( 새로 생긴건가?? )
        // [Swift, Scala, Python, Java, Groovy, Go]

        //Comparator의 compare 메소드는 두가지 인자를 비교해서 값을 리턴.
        //int compare(T o1, T o2)

        lang.stream()
                .sorted(Comparator.comparingInt(String::length)).collect(Collectors.toList());
        // [Go, Java, Scala, Swift, Groovy, Python]

        lang.stream().sorted((s1, s2) -> s2.length() - s1.length()).collect(Collectors.toList());
        // [Groovy, Python, Scala, Swift, Java, Go]
    }
}
