class ActiveSupport::TimeWithZone
    def as_json(options = {})
        strftime('%m/%d/%Y %H:%M:%S')
    end
end