package net.talaatharb.invoicetracker.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import net.talaatharb.invoicetracker.models.User;

public interface FilterUserRepository extends JpaRepository<User,Long> {


    @Query(
            "SELECT e FROM User e WHERE " +
                    "e.englishName IN :names"
    )
    List<User> filterEmployeesByName(List<String>names);
    @Query(
            "SELECT e FROM User e WHERE " +
                    "e.arabicName IN :arabicName "

    )

    List<User> filterEmployeesByArabicName(List<String> arabicName);

    @Query(
            "SELECT e FROM User e WHERE " +
                    "e.jobTitle IN :jobTitle"

    )
    List<User> filterEmployeesByJobTitle(List<String> jobTitle);

    @Query(
            "SELECT e FROM User e WHERE e.id IN :id"

    )
    List<User> filterEmployeesById(List<Long> id);
    @Query(
            "SELECT e FROM User e WHERE e.allowedBalance IN :balance"

    )
    List<User> filterEmployeesByBalance(List<Integer> balance);
    @Query(
            "SELECT e FROM User e WHERE e.remainingBalance IN :remainBalance"

    )
    List<User> filterEmployeesByRemainBalance(List<Integer> remainBalance);

    @Query(
            "SELECT e FROM User e WHERE e.joiningDate = :joinDate"

    )

    List<User> filterEmployeesByJoinDate(Date joinDate);
    @Query(
            "SELECT e FROM User e WHERE e.endDate = :endDate"

    )

    List<User> filterEmployeesByEndDate(Date endDate);
    @Query(
            "SELECT e FROM User e WHERE e.billable = :billable"

    )

    List<User> filterEmployeesByBillable(boolean billable);

    @Query(
            "SELECT e FROM User e WHERE e.isDisabled = :isDisabled"

    )

    List<User> filterEmployeesByIsDisabled(boolean isDisabled);
    @Query(
            "SELECT e FROM User e WHERE e.isFullTime= :isFullTime"

    )

    List<User> filterEmployeesByIsFullTime(boolean isFullTime);

}
