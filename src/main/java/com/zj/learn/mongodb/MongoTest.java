package com.zj.learn.mongodb;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.BSON;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Yangxi on 2017/5/4 0004.
 */
public class MongoTest {
    public static void main(String[] args) {
        MongoClient client = new MongoClient();
        MongoDatabase database = client.getDatabase("test");
        System.out.println(database);

//        database.createCollection("testcollection");

        MongoCollection collection = database.getCollection("testcollection");
        System.out.println(collection.count());

        List<Document> documents = new ArrayList<>();
        Document document = new Document("name","yangxi").append("age","19");
        documents.add(document);
        collection.insertMany(documents);
        List<Person> personList = new ArrayList<>();
        Person person = new Person("yangxi person",23,1);
        personList.add(person);

        MongoCollection<Person> personcollection = database.getCollection("personcollection",Person.class);
        personcollection.insertMany(personList);

        FindIterable iterable = collection.find();
        Iterator iterator = iterable.iterator();
        System.out.println("结果集：");
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }

        FindIterable iterable1 = personcollection.find();
        Iterator iterator1 = iterable1.iterator();
        System.out.println("person结果集：");
        while (iterator1.hasNext()){
            System.out.println(iterator1.next());
        }
    }
}

class Person extends Document {
    private String name;
    private Integer age;
    private Integer sex;

    public Person(String name, Integer age, Integer sex) {
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }
}
