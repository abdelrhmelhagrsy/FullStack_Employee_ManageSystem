export type employeeFilterType = {
  id?: number;
  nationalId?: string;
  employeeId?: string;
  englishName?: string;
  arabicName?: string;
  email?: string;
  mobileNumber?: string;
  englishAddress?: string;
  arabicAddress?: string;
  jobTitle?: string;
  joiningDate?: Date;
  endDate?: Date;
  allowedBalance?: number;
  remainingBalance?: number;
  billable?: string;
  disabled?: string;
  team?: { id: number; name: string }[];
  fullTime?: string;
  resigned?: boolean;
};
