export interface Client {
	  id: number;
    name?: string,
    gender?: string,
	age?: number,
	identifier?: string,
	address?: string,
	phoneNumber?:string,
	password?: string
}
    

export interface BankAccount{
	id?:number,
    bankId?:number,
	clientName: string,
	accountNumber: string,
	accountType: string,
	initialBalance: number,
	currentBalance: number,
	isActive: boolean, 
	clientId?: number
}

export interface Movement{
	id?:number,
	clientId?: number,
	bankAccountId?: number,
	transactionDate?:string,
	movementType?:string,
	amount?:number,
	availableBalance?: number,
	clientName?: string,
	accountNumber?: string,
}

export interface Report{
	 transactionDate? : string,
	 client? : string,
	 accountNumber? : string,
	 accountType? : string,
	 amount? : number,
	 initialBalance? : number,
	 availableBalance? : number,
	 status? : boolean,


}

export type DateRange = {
	startDate : string,
	endDate : string
  }

export type Pageable = {
	size: number,
	page: number
	
}