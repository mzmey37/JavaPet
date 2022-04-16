package com.maksim.javafun.configuration;

import com.maksim.javafun.listener.JobCompletionNotificationListener;
import com.maksim.javafun.step.RowProcessor;
import com.maksim.javafun.step.WFSTWriter;
import com.maksim.javafun.model.Row;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
    @Autowired
    public JobBuilderFactory jobBuilderFactory;
    @Autowired
    public StepBuilderFactory stepBuilderFactory;
    @Bean
    public FlatFileItemReader<Row> reader() {
        return new FlatFileItemReaderBuilder<Row>()
                .name("rowReader")
                .resource(new ClassPathResource("records.csv"))
                .delimited()
                .names(new String[]{"key", "value"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<Row>() {{
                    setTargetType(Row.class);
                }})
                .build();
    }

    @Bean
    RowProcessor processor() {
        return new RowProcessor();
    }

    @Bean
    FlatFileItemWriter<Row> writer() {
        BeanWrapperFieldExtractor<Row> fieldExtractor = new BeanWrapperFieldExtractor<Row>();
        fieldExtractor.setNames(new String[] {"key", "value"});
        return new FlatFileItemWriterBuilder<Row>()
                .name("rowWriter")
                .resource(new FileSystemResource("output/transformed_records.csv"))
                .shouldDeleteIfExists(true)
                .delimited()
                .fieldExtractor(fieldExtractor)
                .build();
    }

    @Bean
    WFSTWriter<Row> writer1() {
        return new WFSTWriter<Row>();
    }

    @Bean
    public Job importUserJob(JobCompletionNotificationListener listener, Step step) {
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .start(step)
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("createSecondCsv")
                .<Row, Row> chunk(100)
                .reader(reader())
                .processor(processor())
                .writer(writer1())
                .build();
    }
}
