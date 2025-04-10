package hris_bank_adapter.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "transaction_notify_email")
public class TransactionNotifyEmail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "category")
    private String category;

    @Column(name = "email_to")
    private String to;

    @Column(name = "email_cc")
    private String cc;
}
