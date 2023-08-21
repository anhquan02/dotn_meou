package com.datn.meou.services;

import com.datn.meou.entity.Account;
import com.datn.meou.entity.Role;
import com.datn.meou.exception.BadRequestException;
import com.datn.meou.model.AccountDTO;
import com.datn.meou.model.LoginDTO;
import com.datn.meou.repository.AccountRepository;
import com.datn.meou.repository.RoleRepository;
import com.datn.meou.security.CustomUserDetails;
import com.datn.meou.security.JwtTokenProvider;
import com.datn.meou.util.DataUtil;
import com.datn.meou.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {
    private final AccountRepository accountRepository;
    private final JwtTokenProvider jwtTokenProvider;

    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> account = this.accountRepository.findByUsernameAndStatus(username, true);
        return account.map(CustomUserDetails::new).orElseThrow(() -> new UsernameNotFoundException(username));
    }


    public UserDetails loadUserById(Long id) {
        Optional<Account> account = this.accountRepository.findByIdAndStatus(id, true);
        return account.map(CustomUserDetails::new).orElseThrow(() -> new UsernameNotFoundException(id.toString()));
    }


    public LoginDTO login(AccountDTO accountDTO) {
        Optional<Account> account = this.accountRepository.findByUsernameAndStatus(accountDTO.getUsername(), true);
        if (account.isPresent()) {
            boolean checkPassword = passwordEncoder.matches(accountDTO.getPassword(), account.get().getPassword());
            if (checkPassword) {
                return generateToken(account);
            }
        }
        throw new BadRequestException("Login thất bại");
    }

    public LoginDTO generateToken(Optional<Account> account) {
        if (account.isPresent()) {
            Account accountMain = account.get();
            String token = jwtTokenProvider.generateToken(new CustomUserDetails(accountMain));
            Optional<Role> role = this.roleRepository.findByIdAndStatus(accountMain.getRoleId(), true);
            return LoginDTO.builder()
                    .id(accountMain.getId())
                    .username(accountMain.getUsername())
                    .token(token)
                    .roleId(accountMain.getRoleId())
                    .nameRole(role.get().getName())
                    .build();
        }
        throw new BadRequestException("Không tìm thấy account");
    }

    public Account createAccount(AccountDTO dto) {
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        Account account = MapperUtil.map(dto, Account.class);
        account.setStatus(true);
        return this.accountRepository.save(account);
    }

    public Object getInfoUser() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }


    public Account getCurrentUser() {
        CustomUserDetails customUserDetails = (CustomUserDetails) this.getInfoUser();
        return customUserDetails.getAccount();
    }

    public List<Account> getAllCustomer(String phone) {
        if (DataUtil.isNullObject(phone)) {
            return this.accountRepository.findByRoleIdAndStatus(3l, true);
        }
        List<Account> accounts = this.accountRepository.findByRoleIdAndStatusAndPhoneContaining(3l, true, phone);
        return accounts;
    }
}
