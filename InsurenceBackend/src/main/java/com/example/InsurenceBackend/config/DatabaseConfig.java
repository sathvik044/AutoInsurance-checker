package com.example.InsurenceBackend.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DatabaseConfig {

    @Bean
    public CommandLineRunner dropConstraint(JdbcTemplate jdbcTemplate) {
        return args -> {
            String[][] tableConstraints = {
                {"users", "role"},
                {"claims", "status"},
                {"claims", "claim_type"},
                {"policies", "status"},
                {"policies", "policy_type"},
                {"claim_documents", "document_type"},
                {"claim_documents", "verification_status"},
                {"fraud_alerts", "indicator"},
                {"fraud_alerts", "status"},
                {"claim_transactions", "transaction_status"},
                {"claim_transactions", "transaction_type"}
            };

            for (String[] pair : tableConstraints) {
                String table = pair[0];
                String column = pair[1];
                String constraint = table + "_" + column + "_check";
                try {
                    jdbcTemplate.execute("ALTER TABLE " + table + " DROP CONSTRAINT IF EXISTS " + constraint);
                } catch (Exception e) {
                    // Ignore errors if constraint doesn't exist or table doesn't exist yet
                }
            }
            System.out.println("✅ Database cleanup: Outdated enum constraints dropped.");
        };
    }
}
