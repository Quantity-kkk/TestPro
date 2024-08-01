package com.kyq.test.yaml;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Description:
 *
 * @author kyq1024
 * @version 1.0
 * @timestamp 2018/9/18
 */
public class ParseYamlTest {

    public static void main(String args[]){
        Yaml yaml = new Yaml();
        try(FileInputStream fileInputStream = new FileInputStream(new File(ParseYamlTest.class.getResource("/").getPath()+"transfer_client.yaml"))) {
            Object load = yaml.load(fileInputStream);
            System.out.println(load);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
