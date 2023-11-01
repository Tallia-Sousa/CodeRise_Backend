package com.example.code.services;

import com.example.code.model.user.CadastroDTO;
import com.example.code.model.user.User;
import com.example.code.model.user.UserRole;
import com.example.code.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class UserService {


    private UserRepository repository;

    private EmailService emailService;

    @Autowired
    public UserService(UserRepository repository, EmailService emailService) {
        this.repository = repository;
        this.emailService = emailService;
    }

    public User cadastrarUsers(CadastroDTO data) {
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.senha());

        User newUser = new User(data.nome(), data.email(), encryptedPassword, UserRole.USER);

        return this.repository.save(newUser);


    }

    public List<User> listarUsuarios() {

        List<User> list = repository.findAll();
        return list;
    }

    public Boolean deletarUsuarios(String id) {
        repository.deleteById(id);
        return true;
    }
// mandar um email
public String solicitarCodigo(String email) {
    User user = (User) repository.findByEmail(email);

    if (user != null) {
        user.setCodigorecuperacaosenha(codigoRecuperacao(user.getId()));
        user.setDataEnvioCodigo(new Date());
        repository.saveAndFlush(user);

        // Enviar e-mail de recuperação de senha
        emailService.sendEmail(user.getEmail(), "Código de Recuperação de senha",
                "Seu codigo de recuperaçao de senha a seguir: " + user.getCodigorecuperacaosenha());

        return "Código enviado com sucesso";
    } else {
        return "Usuário não encontrado";
    }
}




     private String codigoRecuperacao(String id){

         DateFormat format = new SimpleDateFormat("ddMMyyyyHHmmssmm");

         return format.format(new Date())+id;

}
//
//
//    public String alterarSenha(User user){
//        User usuarioN = (User) repository.findByEmailAndCodigorecuperacaosenha(user.getEmail(), user.getSenha());
//        if(usuarioN != null){
//        Date diferenca = new Date(new Date().getTime() - usuarioN.getDataEnvioCodigo().getTime());
//          if(diferenca.getTime() / 1000 < 900){// depois de 15 min apos receber o cod ele expira
//              String esconder = new BCryptPasswordEncoder().encode(user.getSenha());//encriptografar
//                   usuarioN.setSenha(esconder);
//                   usuarioN.setCodigorecuperacaosenha(null); // se tentar recuperar novamente nao vai ser encontrado
//              repository.saveAndFlush(usuarioN);
//              return "Senha alterada com sucesso";
//    } else {
//              return "Tempo expirado, solicite um novo codigo";
//          }}
//          else {
//              return "email ou codigo nao encontados";
//            }
//
//    }//


}