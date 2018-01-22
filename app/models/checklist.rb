class Checklist < ActiveRecord::Base
  ACTIVE = 1
  DEACTIVE = 2
  STATUSES = {'Active' => ACTIVE,'DeActive' => DEACTIVE}
end
