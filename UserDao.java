package by.itacademy.hibernate.dao;


import by.itacademy.hibernate.entity.*;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;

import java.util.Collections;
import java.util.List;

import static by.itacademy.hibernate.entity.QCompany.company;
import static by.itacademy.hibernate.entity.QPayment.payment;
import static by.itacademy.hibernate.entity.QUser.user;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDao {

    private static final UserDao INSTANCE = new UserDao();

    /**
     * Возвращает всех сотрудников
     */
    public List<User> findAll(Session session) {
       /* var cb = session.getCriteriaBuilder();
        var criteria = cb.createQuery(User.class);
        var user = criteria.from(User.class);
        criteria.select(user);
        return session.createQuery(criteria).list();

//     return session.createQuery("select u from User u", User.class).list();*/

        return new JPAQuery<User>(session).select(user).from(user).fetch();
    }

    /**
     * Возвращает всех сотрудников с указанным именем
     */
    public List<User> findAllByFirstName(Session session, String firstName) {
        /*var cb = session.getCriteriaBuilder();
        var criteria = cb.createQuery(User.class);
        var user = criteria.from(User.class);
//        criteria.select(user).where(cb.equal(user.get(User_.personalInfo).get(PersonalInfo_.firstname), firstName));
        return session.createQuery(criteria).list();*/

        return new JPAQuery<User>(session).select(user).from(user)
                .where(user.personalInfo().firstname.eq(firstName))
                .fetch();
    }

    /**
     * Возвращает первые {limit} сотрудников, упорядоченных по дате рождения (в порядке возрастания)
     */
    public List<User> findLimitedUsersOrderedByBirthday(Session session, int limit) {
        return new JPAQuery<User>(session).select(user).from(user)
                .orderBy(new OrderSpecifier(Order.ASC, user.personalInfo().birthDate))
                .limit(limit)
                .fetch();
    }

    /**
     * Возвращает всех сотрудников компании с указанным названием
     */
    public List<User> findAllByCompanyName(Session session, String companyName) {
        return new JPAQuery<User>(session).
                select(user).
                from(company)
                .join(company.users, user)
                .where(company.name.eq(companyName))
                .fetch();
    }

    /**
     * Возвращает все выплаты, полученные сотрудниками компании с указанными именем,
     * упорядоченные по имени сотрудника, а затем по размеру выплаты
     */
    public List<Payment> findAllPaymentsByCompanyName(Session session, String companyName) {
        /*var cb = session.getCriteriaBuilder();
        var criteria = cb.createQuery(Payment.class);
        var payment = criteria.from(Payment.class);
        //       var user = payment.join(Payment_.receiver);
        //       var company = user.join(User_.company);

        *//*criteria.select(payment).where(
                        cb.equal(company.get(Company_.name), companyName)
                )
                .orderBy(
                        cb.asc(user.get(User_.personalInfo).get(PersonalInfo_.firstname)),
                        cb.asc(payment.get(Payment_.amount))
                );*//*

        return session.createQuery(criteria).list();*/
        return new JPAQuery<Payment>(session)
                .select(payment)
                .from(payment)
                .join(payment.receiver(), user)
                .where(user.company().name.eq(companyName))
                .orderBy(user.personalInfo().firstname.asc(),
                        payment.amount.asc())
                .fetch();


    }

    /**
     * Возвращает среднюю зарплату сотрудника с указанными именем и фамилией
     */
    public Double findAveragePaymentAmountByFirstAndLastNames(Session session, String firstName, String lastName) {
        return new JPAQuery<Double>(session)
                .select(payment.amount.avg())
                .from(payment)
                .join(payment.receiver(), user)
                .where(user.personalInfo().firstname.eq(firstName),
                        user.personalInfo().lastname.eq(lastName))
                .fetchOne();
    }

    /**
     * Возвращает для каждой компании: название, среднюю зарплату всех её сотрудников. Компании упорядочены по названию.
     */
    public List<Tuple> findCompanyNamesWithAvgUserPaymentsOrderedByCompanyName(Session session) {
        return new JPAQuery<Tuple>(session)
                .select(company.name, payment.amount.avg())
                .from(company)
                .join(company.users, user)
                .join(user.payments, payment)
                .groupBy(company.name)
                .orderBy(company.name.asc())
                .fetch();
    }

    /**
     * Возвращает список: сотрудник (объект User), средний размер выплат, но только для тех сотрудников, чей средний размер выплат
     * больше среднего размера выплат всех сотрудников
     * Упорядочить по имени сотрудника
     */
    public List<Tuple> isItPossible(Session session) {

        return new JPAQuery<Tuple>(session)
                .select(user, payment.amount.avg())
                .from(user)
                .join(user.payments, payment)
                .groupBy(user.id)
                .having(
                        payment.amount.avg().gt(
                                new JPAQuery<Double>(session)
                                        .select(payment.amount.avg())
                                        .from(payment)
                        )
                )
                .orderBy(user.personalInfo().firstname.asc())
                .fetch();
    }

    public static UserDao getInstance() {
        return INSTANCE;
    }
}