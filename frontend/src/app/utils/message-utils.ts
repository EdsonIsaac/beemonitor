export class MessageUtils {

    static HIVES_GET_FAIL = 'Não foi possível obter a lista de colmeias! Por favor, contate o suporte técnico!';
    static HIVE_DELETE_FAIL = 'Não foi possível excluir os dados da colmeia! ';
    static HIVE_DELETE_SUCCESS = 'Colmeia excluida com sucesso!';
    static HIVE_GET_FAIL = 'Não foi possível obter os dados da colmeia! Por favor, contate o suporte técnico!'
    static HIVE_SAVE_FAIL = 'Não foi possível cadastrar a colmeia! ';
    static HIVE_SAVE_SUCCESS = 'Colmeia cadastrada com sucesso!';
    static HIVE_UPDATE_FAIL = 'Não foi possível atualizar os dados da colmeia! ';
    static HIVE_UPDATE_SUCCESS = 'Dados da colmeia atualizados com sucesso!';

    static MENSURATIONS_GET_FAIL = 'Não foi possível obter a lista de medições! Por favor, contate o suporte técnico!'

    static USERS_GET_FAIL = 'Não foi possível obter a lista de usuários! Por favor, contate o suporte técnico!';
    static USER_GET_FAIL = 'Não foi possível obter os dados do usuário! Por favor, contate o suporte técnico!';
    static USER_SAVE_FAIL = 'Não foi possível cadastrar o usuário! ';
    static USER_SAVE_SUCCESS = 'Usuário cadastrado com sucesso!';
    static USER_UPDATE_FAIL = 'Não foi possível atualizar os dados do usuário! ';
    static USER_UPDATE_SUCCESS = 'Dados do usuário atualizados com sucesso!';

    static getMessage(error: any): string {
        if (Array.isArray(error.erro)) {
          return error.error[0].message;
        }
    
        return error.error.message;
    }
}