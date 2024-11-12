package com.code.camping.utils.dto.response;

import com.code.camping.entity.Wallet;
import lombok.*;

@AllArgsConstructor
@Builder
@Getter
@Setter
@NoArgsConstructor
public class WalletResponse {

    private String id;
    private Integer balance;

    public static WalletResponse fromWallet(Wallet wallet){
        String user_id = (wallet.getUser() != null) ? wallet.getUser().getId() : null;

        return WalletResponse.builder()
                .id(wallet.getId())
                .balance(wallet.getBalance())
                .build();
    }
}
