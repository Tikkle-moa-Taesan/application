package com.ssafy.TmT.domain.account.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.project.domain.Account;
import java.util.List;

@Repository
public class AccountDAO {

    private final SqlSession sqlSession;

    private static final String NAMESPACE = "com.example.project.dao.AccountDAO";

    @Autowired
    public AccountDAO(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    // 모든 계좌 조회
    public List<Account> findAllAccounts() {
        return sqlSession.selectList(NAMESPACE + ".findAllAccounts");
    }

    // ID로 계좌 조회
    public Account findAccountById(Long id) {
        return sqlSession.selectOne(NAMESPACE + ".findAccountById", id);
    }
}
