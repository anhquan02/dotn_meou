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
import java.util.regex.Pattern;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {

    private static final Pattern EMAIL = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private static final Pattern PHONE = Pattern.compile("^\\d{10}$", Pattern.CASE_INSENSITIVE);
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

    public Account register(AccountDTO dto){
        if(DataUtil.isNullObject(dto.getUsername())){
            throw new BadRequestException("username không được để trống");
        }
        if(DataUtil.isNullObject(dto.getPassword())){
            throw new BadRequestException("password không được để trống");
        }
        if(dto.getPassword().length() < 6){
            throw new BadRequestException("password tối thiểu 6 ký tự");
        }
        if(DataUtil.isNullObject(dto.getEmail())){
            throw new BadRequestException("email không được để trống");
        }
        if(DataUtil.isNullObject(dto.getAddress())){
            throw new BadRequestException("địa chỉ không được để trống");
        }
        if(DataUtil.isNullObject(dto.getFullname())){
            throw new BadRequestException("Tên không được để trống");
        }
        if(DataUtil.isNullObject(dto.getPhone())){
            throw new BadRequestException("Số điện thoại không được để trống");
        }
        if(accountRepository.findByUsername(dto.getUsername()).isPresent()){
            throw new BadRequestException("username đã tồn tại");
        }
        if(isEmail(dto.getEmail()) == false){
            throw new BadRequestException("email không đúng định dạng");
        }
        if(isPhone(dto.getPhone()) == false){
            throw new BadRequestException("Số điện thoại không đúng định dạng");
        }
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        Account account = MapperUtil.map(dto, Account.class);
        account.setStatus(true);
        account.setRoleId(3L);
        Account account1 = accountRepository.save(account);
        if(account1.getId() > 0){
            return account;
        }
        throw new BadRequestException("Thêm mới tài khoản thất bại");
    }

    public Account changePassward(AccountDTO dto){

        if(DataUtil.isNullObject(dto.getUsername())){
            throw new BadRequestException("username không được trống");
        }
        if(DataUtil.isNullObject(dto.getPassword())){
            throw  new BadRequestException("password không được để trống");
        }
        if(DataUtil.isNullObject(dto.getResetPassword())){
            throw  new BadRequestException("password mới không được để trống");
        }
        if(DataUtil.isNullObject(dto.getResetPassword2())){
            throw  new BadRequestException("nhập lại password không được để trống");
        }
        Optional<Account> account = accountRepository.findByUsername(dto.getUsername());
        if(account.isEmpty()){
            throw new BadRequestException("Tài khoản không tồn tại");
        }
        if(!account.get().getPassword().equals(dto.getPassword().trim())){
            throw new BadRequestException("sai mật khẩu");
        }
        if(!dto.getResetPassword().trim().equals(dto.getResetPassword2().trim())){
            throw new BadRequestException("nhập lại mật khẩu không trùng với mật khẩu mới");
        }
        account.get().setPassword(dto.getPassword());
        return accountRepository.save(account.get());
    }

    public static boolean isEmail(String s) {
        Boolean x = EMAIL.matcher(s).matches();
        return x;
    }
    public static boolean isPhone(String s){
        Boolean x = PHONE.matcher(s).matches();
        return x;

    }

}
