export interface Authentication {
    username: string;
    access_token: string;
    expires: number;
    profile: string;
    roles: Array<string>;
}